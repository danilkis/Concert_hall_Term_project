package Crew.Navigation

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Screens
 */
enum class Screen(
    val label: String,
    val icon: ImageVector
) {
    HomeScreen(
        label = "Оборудавание",
        icon = Icons.Default.Speaker
    ),
    NotificationsScreen(
        label = "Сектора",
        icon = Icons.Default.Segment
    ),
    SettingsScreen(
        label = "Сцены",
        icon = Icons.Default.Stadium
    ),
    ProfileScreens(
        label = "Концерты",
        icon = Icons.Default.SpeakerGroup
    )
}