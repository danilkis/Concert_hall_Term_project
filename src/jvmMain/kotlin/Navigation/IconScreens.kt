package Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(
    val label: String,
    val icon: ImageVector
) {
    CrewScreen(
        label = "Команда",
        icon = Icons.Filled.List
    ),
    ManagerScreen(
        label = "Менеджер",
        icon = Icons.Filled.LocationOn
    ),
    TicketScreen(
        label = "Билеты",
        icon = Icons.Filled.Create
    ),
    AuthScreen(
        label = "Авторизация",
        icon = Icons.Filled.Create
    )
}