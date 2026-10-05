package top.wkbin.taixu.runtime.privilege

import android.content.pm.PackageManager
import rikka.shizuku.Shizuku
import java.util.concurrent.TimeUnit

/**
 * 特权探测结果 TTL 缓存。
 *
 * 避免 [PrivilegeManager.getPrivilegeInfo] 每次 host 动作 / health 轮询
 * 都 fork `su -c "echo ok"`（≤3s 阻塞）或做 2 次 Binder 往返。
 * 权限模式切换或降级时调用 [invalidate] 清除缓存。
 */
class PrivilegeProbeCache(private val ttlMs: Long = 30_000L) {

    @Volatile private var lastProbeAtMs: Long = 0L
    @Volatile private var shizukuAvailable: Boolean = false
    @Volatile private var rootAvailable: Boolean = false

    /** 30s 内直接返回上次探测结果，否则执行 [probe] 并缓存。 */
    fun withCache(
        shizukuProbe: () -> Boolean,
        rootProbe: () -> Boolean,
        needRoot: Boolean,
    ): Pair<Boolean, Boolean> {
        val now = System.currentTimeMillis()
        val hit = (now - lastProbeAtMs) < ttlMs && lastProbeAtMs > 0L
        if (hit) return shizukuAvailable to rootAvailable

        val shizuku = shizukuProbe()
        val root = if (needRoot) rootProbe() else false
        shizukuAvailable = shizuku
        rootAvailable = root
        lastProbeAtMs = now
        return shizuku to root
    }

    /** 权限失效降级时调用，强制下次重新探测。 */
    fun invalidate() {
        lastProbeAtMs = 0L
        shizukuAvailable = false
        rootAvailable = false
    }
}
