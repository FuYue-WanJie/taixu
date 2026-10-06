package top.wkbin.taixu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.rememberVectorPainterResource
import com.google.android.material.icons.Icons
import com.google.android.material.icons.outlined.*

/**
 * 统一图标映射表。
 *
 * A 类（通用 UI）→ Material Symbols extended 库 ImageVector。
 * B/C 类（品牌/AI Provider Logo）→ 保留 XML drawable，通过 Painter 使用。
 */
object AppIcons {

    // ── A 类：通用 UI（Material Symbols extended）─────────────────

    val Admin = Icons.Outlined.AdminPanelSettings
    val Alert = Icons.Outlined.Warning
    val ArrowUp = Icons.Outlined.ArrowUpward
    val Attach = Icons.Outlined.AttachFile
    val Back = Icons.Outlined.ArrowBack
    val Battery = Icons.Outlined.BatteryFull
    val Bot = Icons.Outlined.SmartToy
    val Brain = Icons.Outlined.AutoAwesome
    val Bug = Icons.Outlined.BugReport
    val Cable = Icons.Outlined.Connection
    val Chat = Icons.Outlined.Chat
    val Check = Icons.Outlined.Check
    val ChevronDown = Icons.Outlined.ExpandMore
    val ChevronRight = Icons.Outlined.ChevronRight
    val ChevronUp = Icons.Outlined.ExpandLess
    val Close = Icons.Outlined.Close
    val Code = Icons.Outlined.Code
    val Community = Icons.Outlined.Community
    val Compress = Icons.Outlined.Compress
    val Copy = Icons.Outlined.ContentCopy
    val Cpu = Icons.Outlined.Memory
    val Document = Icons.Outlined.Description
    val Download = Icons.Outlined.Download
    val Edit = Icons.Outlined.Edit
    val Extension = Icons.Outlined.Extensions
    val File = Icons.Outlined.InsertDriveFile
    val Folder = Icons.Outlined.Folder
    val FolderDownload = Icons.Outlined.FolderDownload
    val FolderOpen = Icons.Outlined.FolderOpen
    val FontSize = Icons.Outlined.FormatSize
    val GitBranch = Icons.Outlined.GitBranch
    val GitCommit = Icons.Outlined.GitCommit
    val Globe = Icons.Outlined.Public
    val Home = Icons.Outlined.Home
    val Hub = Icons.Outlined.Hub
    val ImageIcon = Icons.Outlined.Image
    val Info = Icons.Outlined.Info
    val Key = Icons.Outlined.Key
    val Link = Icons.Outlined.Link
    val List = Icons.Outlined.List
    val Logs = Icons.Outlined.DataObject
    val Mail = Icons.Outlined.Email
    val Model = Icons.Outlined.ModelTraining
    val More = Icons.Outlined.MoreVert
    val Mount = Icons.Outlined.HardDrive
    val NavDashboard = Icons.Outlined.Dashboard
    val NavMessage = Icons.Outlined.Forum
    val NavRepository = Icons.Outlined.Inventory
    val NavSettings = Icons.Outlined.Settings
    val Network = Icons.Outlined.CloudLaptop
    val OpenInNew = Icons.Outlined.OpenInNew
    val Package = Icons.Outlined.Package
    val Palette = Icons.Outlined.Palette
    val Play = Icons.Outlined.PlayArrow
    val Plus = Icons.Outlined.Add
    val PowerSettings = Icons.Outlined.PowerSettingsNew
    val Prompt = Icons.Outlined.Style
    val Refresh = Icons.Outlined.Refresh
    val Reverse = Icons.Outlined.RotateLeft
    val Save = Icons.Outlined.Save
    val SdCard = Icons.Outlined.SdCard
    val Search = Icons.Outlined.Search
    val Server = Icons.Outlined.Dns
    val Settings = Icons.Outlined.Settings
    val Shield = Icons.Outlined.Shield
    val Sparkles = Icons.Outlined.AutoAwesome
    val Speed = Icons.Outlined.Speed
    val Sponsor = Icons.Outlined.Handshake
    val Stop = Icons.Outlined.Stop
    val Storage = Icons.Outlined.Storage
    val Terminal = Icons.Outlined.Terminal
    val Trash = Icons.Outlined.Delete
    val Tune = Icons.Outlined.Tune
    val Update = Icons.Outlined.SystemUpdateAlt
    val Vibrate = Icons.Outlined.Vibration
    val Visibility = Icons.Outlined.Visibility
    val VisibilityOff = Icons.Outlined.VisibilityOff
    val Workspace = Icons.Outlined.FolderSpecial
    val Wrench = Icons.Outlined.Build

    // ── B 类：品牌 Logo（保留 XML）─────────────────────────────

    @Composable fun LogoAlpine(): Painter = rememberVectorPainterResource(R.drawable.components_ic_logo_alpine)
    @Composable fun LogoAndroid(): Painter = rememberVectorPainterResource(R.drawable.components_ic_logo_android)
    @Composable fun LogoArch(): Painter = rememberVectorPainterResource(R.drawable.components_ic_logo_arch)
    @Composable fun LogoDebian(): Painter = rememberVectorPainterResource(R.drawable.components_ic_logo_debian)
    @Composable fun LogoFedora(): Painter = rememberVectorPainterResource(R.drawable.components_ic_logo_fedora)
    @Composable fun LogoFlutter(): Painter = rememberVectorPainterResource(R.drawable.components_ic_logo_flutter)
    @Composable fun LogoKali(): Painter = rememberVectorPainterResource(R.drawable.components_ic_logo_kali)
    @Composable fun LogoLinux(): Painter = rememberVectorPainterResource(R.drawable.components_ic_logo_linux)
    @Composable fun LogoUbuntu(): Painter = rememberVectorPainterResource(R.drawable.components_ic_logo_ubuntu)
    @Composable fun LogoVoid(): Painter = rememberVectorPainterResource(R.drawable.components_ic_logo_void)
    @Composable fun LogoGithub(): Painter = rememberVectorPainterResource(R.drawable.components_ic_github)
    @Composable fun LogoQQ(): Painter = rememberVectorPainterResource(R.drawable.components_ic_qq)

    // ── C 类：AI Provider Logo（保留 XML）─────────────────────────

    @Composable fun ProviderAnthropic(): Painter = rememberVectorPainterResource(R.drawable.components_ic_provider_anthropic)
    @Composable fun ProviderDeepSeek(): Painter = rememberVectorPainterResource(R.drawable.components_ic_provider_deepseek)
    @Composable fun ProviderGemini(): Painter = rememberVectorPainterResource(R.drawable.components_ic_provider_gemini)
    @Composable fun ProviderKimi(): Painter = rememberVectorPainterResource(R.drawable.components_ic_provider_kimi)
    @Composable fun ProviderLMStudio(): Painter = rememberVectorPainterResource(R.drawable.components_ic_provider_lmstudio)
    @Composable fun ProviderMiniMax(): Painter = rememberVectorPainterResource(R.drawable.components_ic_provider_minimax)
    @Composable fun ProviderMistral(): Painter = rememberVectorPainterResource(R.drawable.components_ic_provider_mistral)
    @Composable fun ProviderNvidia(): Painter = rememberVectorPainterResource(R.drawable.components_ic_provider_nvidia)
    @Composable fun ProviderOllama(): Painter = rememberVectorPainterResource(R.drawable.components_ic_provider_ollama)
    @Composable fun ProviderOpenAI(): Painter = rememberVectorPainterResource(R.drawable.components_ic_provider_openai)
    @Composable fun ProviderOpenRouter(): Painter = rememberVectorPainterResource(R.drawable.components_ic_provider_openrouter)
    @Composable fun ProviderQwen(): Painter = rememberVectorPainterResource(R.drawable.components_ic_provider_qwen)
}
