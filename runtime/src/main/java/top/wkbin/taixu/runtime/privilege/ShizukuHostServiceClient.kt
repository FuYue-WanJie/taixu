package top.wkbin.taixu.runtime.privilege

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import org.json.JSONObject
import rikka.shizuku.Shizuku

/** 应用进程侧的 Shizuku UserService 连接与 AIDL 调用器。 */
class ShizukuHostServiceClient(
    context: Context,
) {
    private val serviceArgs = Shizuku.UserServiceArgs(
        ComponentName(context.packageName, ShizukuHostUserService::class.java.name),
    )
        .processNameSuffix("taixu_host")
        .tag("taixu-host-shell-v1")
        .version(1)
        .daemon(false)
        .debuggable(false)

    private val connectionMutex = Mutex()
    @Volatile private var service: IShizukuHostService? = null
    @Volatile private var pendingConnection: CompletableDeferred<IShizukuHostService>? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            val connected = IShizukuHostService.Stub.asInterface(binder)
            service = connected
            Log.d("ShizukuHostSvc", "connected")
            pendingConnection?.complete(connected)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            service = null
            pendingConnection?.completeExceptionally(IllegalStateException("Shizuku UserService 连接已断开"))
        }

        override fun onBindingDied(name: ComponentName) {
            service = null
            pendingConnection?.completeExceptionally(IllegalStateException("Shizuku UserService Binder 已失效"))
        }

        override fun onServiceDetached(name: ComponentName, binder: IBinder) {
            Log.d("ShizukuHostSvc", "detached")
        }
    }

    /**
     * 预热绑定：权限确认 ACTIVE 后提前完成冷绑定，避免首次 host 调用等 10-30s。
     */
    suspend fun warmUp() {
        runCatching { requireService() }
            .onFailure { Log.d("ShizukuHostSvc", "warmUp deferred: ${it.message}") }
    }

    /**
     * 执行宿主侧命令。返回 [ShellExecResult]，与 [PrivilegeManager.executeShellCommand] 共享结构。
     */
    suspend fun execute(operationId: String, command: String): ShellExecResult = withContext(Dispatchers.IO) {
        val svc = requireService()
        val raw = svc.execute(operationId, command)
        val json = JSONObject(raw)
        val exitCode = json.optInt("exitCode", -1)
        val stdout = json.optString("stdout")
        val stderr = json.optString("stderr")
        val success = json.optBoolean("success", false)
        if (success) {
            ShellExecResult(true, exitCode, stdout, stderr)
        } else {
            val reason = json.optString("reason", "未知原因")
            ShellExecResult(false, exitCode, stdout, "命令执行失败: $reason\n$stderr".trim())
        }
    }

    /**
     * 取消宿主侧运行中的命令。
     */
    fun cancel(operationId: String): Boolean {
        val svc = service ?: return false
        return runCatching { svc.cancel(operationId) }.getOrDefault(false)
    }

    /** 获取宿主侧当前前台应用包名。 */
    suspend fun foregroundPackage(): String? = withContext(Dispatchers.IO) {
        val svc = requireService()
        val raw = svc.getForegroundPackage()
        val json = JSONObject(raw)
        json.optString("packageName").takeIf { it.isNotEmpty() }
    }

    /**
     * 获取宿主侧系统设置键值。
     */
    suspend fun settingsGet(namespace: String, key: String): String? = withContext(Dispatchers.IO) {
        val svc = requireService()
        val raw = svc.getSettings(namespace, key)
        val json = JSONObject(raw)
        json.optString("value").takeIf { it.isNotEmpty() }
    }

    /**
     * 设置宿主侧系统设置键值。
     */
    suspend fun settingsPut(namespace: String, key: String, value: String) {
        withContext(Dispatchers.IO) {
            val svc = requireService()
            svc.putSettings(namespace, key, value)
        }
    }

    /**
     * 查询用户侧应用包是否已安装。
     */
    suspend fun isPackageInstalledUser(packageName: String, userId: Int): Boolean = withContext(Dispatchers.IO) {
        val svc = requireService()
        val json = JSONObject(svc.isPackageInstalledUser(packageName, userId))
        json.optBoolean("installed", false)
    }

    /**
     * 冻结/解冻应用。
     */
    suspend fun setAppOperabilityFrozen(frozen: Boolean) {
        withContext(Dispatchers.IO) {
            val svc = requireService()
            svc.setAppOperabilityFrozen(frozen)
        }
    }

    private suspend fun requireService(): IShizukuHostService {
        connectionMutex.withLock {
            val current = service
            if (current != null && runCatching { current.pingBinder() }.getOrDefault(false)) {
                return current
            }
            Shizuku.pingBinder()
            if (Shizuku.checkSelfPermission() != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                error("Shizuku 未授权")
            }
            current?.let {
                runCatching { Shizuku.unbindApiService(it) }
                service = null
            }
            val deferred = CompletableDeferred<IShizukuHostService>()
            pendingConnection = deferred
            var lastException: Exception? = null
            repeat(BIND_MAX_RETRIES) { attempt ->
                try {
                    Log.d("ShizukuHostSvc", "bind attempt ${attempt + 1}")
                    Shizuku.bindApiService(serviceArgs, connection)
                    return@withLock withTimeout(CONNECTION_TIMEOUT_MS) { deferred.await() }
                } catch (e: Exception) {
                    lastException = e
                    if (attempt < BIND_MAX_RETRIES - 1) {
                        kotlinx.coroutines.delay(BIND_RETRY_DELAY_MS)
                    }
                }
            }
            pendingConnection = null
            error("绑定 Shizuku UserService 失败: ${lastException?.message ?: "未知原因"}")
        }
    }

    private suspend fun bindOnce(): IShizukuHostService {
        val deferred = CompletableDeferred<IShizukuHostService>()
        pendingConnection = deferred
        Shizuku.bindApiService(serviceArgs, connection)
        val svc = withTimeout(CONNECTION_TIMEOUT_MS) { deferred.await() }
        pendingConnection = null
        return svc
    }

    companion object {
        /**
         * 冷绑定超时。Shizuku fork 独立进程冷启需 10-20s（中低端），
         * 给 30s 覆盖最坏情况；已绑定状态下不走此路径。
         */
        private const val CONNECTION_TIMEOUT_MS = 30_000L
        private const val BIND_MAX_RETRIES = 2
        private const val BIND_RETRY_DELAY_MS = 500L
    }
}
