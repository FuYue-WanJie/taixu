package top.wkbin.taixu.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import top.wkbin.taixu.feature.components.R

enum class RuntimeIconName {
    Home, Workspace, Terminal, Settings, Back, ChevronRight, ChevronDown, ChevronUp, Package,
    NavDashboard, NavMessage, NavRepository, NavSettings,
    Refresh, Shield, Storage, Globe, Trash, Close, Check, Alert, Logs,
    Download, Play, Stop, More, Plus, Chat, List, Copy,
    Folder, File, Code, Edit, Save, ArrowUp, Cpu, Search, Info,
    Image, Attach,
    // 官方精准品牌与系统/框架 Logo (B/C 类：保留 XML)
    Linux, Debian, Ubuntu, Arch, Kali, Fedora, Alpine, Void,
    Android, Flutter,
    Github, Qq,
    Bot, Palette, FontSize, Battery, Bug, Update, Extension, Hub, Mount, OpenInNew, Key, Tune,
    Brain, Sparkles, Vibrate, FolderDownload, Document, SdCard, Server, Compress,
    Prompt, Wrench, Model, Network, Community, FolderOpen, Speed, Cable, Admin, Link,
    Reverse, PowerSettingsNew, Visibility, VisibilityOff, Sponsor, Mail,
    GitBranch, GitCommit,
}

/**
 * 统一图标入口：
 * - A 类通用图标 → material-icons-extended 库 ImageVector（经 AppIcons 映射）
 * - B/C 类品牌 Logo → 保留 XML drawable（painterResource）
 * 单色图标默认跟随内容色 tint，彩色品牌 Logo 保持原色。
 */
@Composable
fun RuntimeIcon(
    name: RuntimeIconName,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
) {
    // A 类：从 material-icons-extended 库获取 ImageVector
    val imageVector: ImageVector? = when (name) {
        RuntimeIconName.Home -> AppIcons.Home
        RuntimeIconName.Workspace -> AppIcons.Workspace
        RuntimeIconName.Terminal -> AppIcons.Terminal
        RuntimeIconName.Settings -> AppIcons.Settings
        RuntimeIconName.Back -> AppIcons.Back
        RuntimeIconName.ChevronRight -> AppIcons.ChevronRight
        RuntimeIconName.ChevronDown -> AppIcons.ChevronDown
        RuntimeIconName.ChevronUp -> AppIcons.ChevronUp
        RuntimeIconName.Package -> AppIcons.Package
        RuntimeIconName.NavDashboard -> AppIcons.NavDashboard
        RuntimeIconName.NavMessage -> AppIcons.NavMessage
        RuntimeIconName.NavRepository -> AppIcons.NavRepository
        RuntimeIconName.NavSettings -> AppIcons.NavSettings
        RuntimeIconName.Refresh -> AppIcons.Refresh
        RuntimeIconName.Shield -> AppIcons.Shield
        RuntimeIconName.Storage -> AppIcons.Storage
        RuntimeIconName.Globe -> AppIcons.Globe
        RuntimeIconName.Trash -> AppIcons.Trash
        RuntimeIconName.Close -> AppIcons.Close
        RuntimeIconName.Check -> AppIcons.Check
        RuntimeIconName.Alert -> AppIcons.Alert
        RuntimeIconName.Logs -> AppIcons.Logs
        RuntimeIconName.Download -> AppIcons.Download
        RuntimeIconName.Play -> AppIcons.Play
        RuntimeIconName.Stop -> AppIcons.Stop
        RuntimeIconName.More -> AppIcons.More
        RuntimeIconName.Plus -> AppIcons.Plus
        RuntimeIconName.Chat -> AppIcons.Chat
        RuntimeIconName.List -> AppIcons.List
        RuntimeIconName.Copy -> AppIcons.Copy
        RuntimeIconName.Folder -> AppIcons.Folder
        RuntimeIconName.File -> AppIcons.File
        RuntimeIconName.Code -> AppIcons.Code
        RuntimeIconName.Edit -> AppIcons.Edit
        RuntimeIconName.Save -> AppIcons.Save
        RuntimeIconName.ArrowUp -> AppIcons.ArrowUp
        RuntimeIconName.Cpu -> AppIcons.Cpu
        RuntimeIconName.Search -> AppIcons.Search
        RuntimeIconName.Info -> AppIcons.Info
        RuntimeIconName.Image -> AppIcons.ImageIcon
        RuntimeIconName.Attach -> AppIcons.Attach
        RuntimeIconName.Bot -> AppIcons.Bot
        RuntimeIconName.Palette -> AppIcons.Palette
        RuntimeIconName.FontSize -> AppIcons.FontSize
        RuntimeIconName.Battery -> AppIcons.Battery
        RuntimeIconName.Bug -> AppIcons.Bug
        RuntimeIconName.Update -> AppIcons.Update
        RuntimeIconName.Extension -> AppIcons.Extension
        RuntimeIconName.Hub -> AppIcons.Hub
        RuntimeIconName.Mount -> AppIcons.Mount
        RuntimeIconName.OpenInNew -> AppIcons.OpenInNew
        RuntimeIconName.Key -> AppIcons.Key
        RuntimeIconName.Tune -> AppIcons.Tune
        RuntimeIconName.Brain -> AppIcons.Brain
        RuntimeIconName.Sparkles -> AppIcons.Sparkles
        RuntimeIconName.Vibrate -> AppIcons.Vibrate
        RuntimeIconName.FolderDownload -> AppIcons.FolderDownload
        RuntimeIconName.Document -> AppIcons.Document
        RuntimeIconName.SdCard -> AppIcons.SdCard
        RuntimeIconName.Server -> AppIcons.Server
        RuntimeIconName.Compress -> AppIcons.Compress
        RuntimeIconName.Prompt -> AppIcons.Prompt
        RuntimeIconName.Wrench -> AppIcons.Wrench
        RuntimeIconName.Model -> AppIcons.Model
        RuntimeIconName.Network -> AppIcons.Network
        RuntimeIconName.Community -> AppIcons.Community
        RuntimeIconName.FolderOpen -> AppIcons.FolderOpen
        RuntimeIconName.Speed -> AppIcons.Speed
        RuntimeIconName.Cable -> AppIcons.Cable
        RuntimeIconName.Admin -> AppIcons.Admin
        RuntimeIconName.Link -> AppIcons.Link
        RuntimeIconName.Reverse -> AppIcons.Reverse
        RuntimeIconName.PowerSettingsNew -> AppIcons.PowerSettings
        RuntimeIconName.Visibility -> AppIcons.Visibility
        RuntimeIconName.VisibilityOff -> AppIcons.VisibilityOff
        RuntimeIconName.Sponsor -> AppIcons.Sponsor
        RuntimeIconName.Mail -> AppIcons.Mail
        RuntimeIconName.GitBranch -> AppIcons.GitBranch
        RuntimeIconName.GitCommit -> AppIcons.GitCommit
        // B/C 类：保留 XML drawable，返回 null 走下方 painterResource 路径
        else -> null
    }

    val isColorfulBrand = name in COLORFUL_BRAND_ICONS
    val effectiveTint = when {
        isColorfulBrand && tint == Color.Unspecified -> Color.Unspecified
        tint != Color.Unspecified -> tint
        else -> LocalContentColor.current
    }

    if (imageVector != null) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = modifier,
            tint = effectiveTint,
        )
    } else {
        // B/C 类：品牌 Logo 走 painterResource
        val resId = when (name) {
            RuntimeIconName.Linux -> R.drawable.components_ic_logo_linux
            RuntimeIconName.Debian -> R.drawable.components_ic_logo_debian
            RuntimeIconName.Ubuntu -> R.drawable.components_ic_logo_ubuntu
            RuntimeIconName.Arch -> R.drawable.components_ic_logo_arch
            RuntimeIconName.Kali -> R.drawable.components_ic_logo_kali
            RuntimeIconName.Fedora -> R.drawable.components_ic_logo_fedora
            RuntimeIconName.Alpine -> R.drawable.components_ic_logo_alpine
            RuntimeIconName.Void -> R.drawable.components_ic_logo_void
            RuntimeIconName.Android -> R.drawable.components_ic_logo_android
            RuntimeIconName.Flutter -> R.drawable.components_ic_logo_flutter
            RuntimeIconName.Github -> R.drawable.components_ic_github
            RuntimeIconName.Qq -> R.drawable.components_ic_qq
            else -> R.drawable.components_ic_home
        }
        Icon(
            painter = androidx.compose.ui.res.painterResource(resId),
            contentDescription = null,
            modifier = modifier,
            tint = effectiveTint,
        )
    }
}

/** 多色品牌 Logo 集合：不参与 tint，展示资源原始配色 */
private val COLORFUL_BRAND_ICONS = setOf(
    RuntimeIconName.Debian,
    RuntimeIconName.Ubuntu,
    RuntimeIconName.Arch,
    RuntimeIconName.Kali,
    RuntimeIconName.Fedora,
    RuntimeIconName.Alpine,
    RuntimeIconName.Void,
    RuntimeIconName.Android,
    RuntimeIconName.Flutter,
)

/**
 * 根据发行版标识返回专有官方 Linux 发行版 Logo
 */
fun distroIconFor(distroId: String): RuntimeIconName = when (distroId.lowercase()) {
    "debian" -> RuntimeIconName.Debian
    "ubuntu" -> RuntimeIconName.Ubuntu
    "arch", "archlinux" -> RuntimeIconName.Arch
    "kali" -> RuntimeIconName.Kali
    "fedora" -> RuntimeIconName.Fedora
    "alpine" -> RuntimeIconName.Alpine
    "void" -> RuntimeIconName.Void
    else -> RuntimeIconName.Linux
}
