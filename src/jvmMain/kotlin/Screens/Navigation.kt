package Screens

import Navigation.NavController
import Navigation.NavigationHost
import Navigation.Screen
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
            EventEquipmentScreen(navController)
        }

        composable(Screen.ProfileScreens.name) {
            StagesScreen(navController)
        }

    }.build()
}