package Manager.Navigation

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
enum class Screen( //TODO: Иконки
    val label: String,
    val icon: ImageVector
) {
    ArtistScreen(
        label = "Исполнители",
        icon = Icons.Filled.List
    ),
    EventScreen(
        label = "События",
        icon = Icons.Filled.LocationOn
    ),
    CrewScreen(
        label = "Рабочие",
        icon = Icons.Filled.Create
    ),
    PlanningScreen(
        label = "Планирование",
        icon = Icons.Filled.Build
    )
}