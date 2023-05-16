package Tickets.Navigation

import Crew.Screens.EquipmentScreen
import Crew.Screens.SectorsScreen
import Crew.Screens.StageInfoScreen
import Navigation.NavController
import Navigation.NavigationHost
import Navigation.composable
import Tickets.TicketScreen
import androidx.compose.runtime.Composable

@Composable
fun CustomNavigationHostTickets(
    navController: NavController
) {
    NavigationHost(navController) {
        composable(Tickets.Navigation.Screen.TicketScreen.name) {
            TicketScreen(navController) //TODO: Заменить
        }

        composable(Tickets.Navigation.Screen.VisitorsScreen.name) {
            SectorsScreen(navController)
        }

        composable(Tickets.Navigation.Screen.StagesScreen.name) {
            StageInfoScreen(navController)
        }
    }.build()
}