package Navigation

import Crew.CrewWindow
import Crew.Navigation.Screen
import Crew.Screens.EquipmentScreen
import Crew.Screens.EventEquipmentScreen
import Crew.Screens.SectorsScreen
import Crew.Screens.StageInfoScreen
import androidx.compose.runtime.Composable

@Composable
fun CustomNavigationHostGlobal(
    navController: NavController
) {
    NavigationHost(navController) {
        composable(Navigation.Screen.CrewScreen.name) {
            CrewWindow(navController)
        }

        composable(Screen.NotificationsScreen.name) {
            SectorsScreen(navController)
        }

        composable(Screen.SettingsScreen.name) {
            StageInfoScreen(navController)
        }

        composable(Screen.ProfileScreens.name) {
            EventEquipmentScreen(navController)
        }

    }.build()
}