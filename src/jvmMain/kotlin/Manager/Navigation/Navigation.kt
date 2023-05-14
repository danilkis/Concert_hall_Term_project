package Manager.Navigation

import Navigation.NavController
import Navigation.NavigationHost
import Manager.Navigation.Screen
import Navigation.composable
import Screens.ArtistScreen
import Screens.CrewScreen
import Screens.EventScreen
import Screens.PlanningScreen
import androidx.compose.runtime.Composable

@Composable
fun CustomNavigationHostManager(
    navController: NavController
) {
    NavigationHost(navController) {
        composable(Screen.ArtistScreen.name) {
            ArtistScreen(navController)
        }

        composable(Screen.EventScreen.name) {
            EventScreen(navController)
        }

        composable(Screen.CrewScreen.name) {
            CrewScreen(navController)
        }

        composable(Screen.PlanningScreen.name) {
            PlanningScreen(navController)
        }

    }.build()
}