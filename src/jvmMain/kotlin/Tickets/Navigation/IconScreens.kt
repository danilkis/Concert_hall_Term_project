package Tickets.Navigation

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
    TicketScreen(
        label = "Билеты",
        icon = Icons.Filled.List
    ),
    VisitorsScreen(
        label = "Посетители",
        icon = Icons.Filled.LocationOn
    ),
    StagesScreen(
        label = "Сцены",
        icon = Icons.Filled.Create
    )
}