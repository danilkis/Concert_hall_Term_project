package Manager.Navigation

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
    ArtistScreen(
        label = "Исполнители",
        icon = Icons.Default.Mic
    ),
    EventScreen(
        label = "События",
        icon = Icons.Default.Event
    ),
    CrewScreen(
        label = "Рабочие",
        icon = Icons.Default.Person
    ),
    PlanningScreen(
        label = "Планирование",
        icon = Icons.Default.Stadium
    )
}