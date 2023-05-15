package Crew.Navigation

import Crew.Screens.EquipmentScreen
import Crew.Screens.EventEquipmentScreen
import Crew.Screens.SectorsScreen
import Crew.Screens.StageInfoScreen
import Navigation.NavController
import Navigation.NavigationHost
import Crew.Navigation.Screen
import Navigation.composable
import androidx.compose.runtime.Composable

@Composable
fun CustomNavigationHost(
    navController: NavController
) {
    NavigationHost(navController) {
        composable(Screen.HomeScreen.name) {
            EquipmentScreen(navController)
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