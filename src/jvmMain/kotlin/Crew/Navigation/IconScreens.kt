package Crew.Navigation

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Screens
 */
enum class Screen(
    val label: String,
    val icon: ImageVector
) { //TODO: Иконки
    HomeScreen(
        label = "Оборудавание",
        icon = Icons.Filled.List
    ),
    NotificationsScreen(
        label = "Сектора",
        icon = Icons.Filled.LocationOn
    ),
    SettingsScreen(
        label = "Сцены",
        icon = Icons.Filled.Create
    ),
    ProfileScreens(
        label = "Оборудование на концертах",
        icon = Icons.Filled.Build
    )
}