package Tickets.Navigation

import Crew.Screens.EquipmentScreen
import Crew.Screens.SectorsScreen
import Crew.Screens.StageInfoScreen
import Navigation.NavController
import Navigation.NavigationHost
import Navigation.Screen
import Navigation.composable
import Tickets.AttendeesScreen
import Tickets.TicketScreen
import androidx.compose.runtime.Composable

@Composable
fun CustomNavigationHostTickets(
    navController: NavController
) {
    NavigationHost(navController) {
        composable(Tickets.Navigation.Screen.TicketScreen.name) {
            TicketScreen(navController)
        }

        composable(Tickets.Navigation.Screen.VisitorsScreen.name) {
            AttendeesScreen(navController)
        }

        composable(Tickets.Navigation.Screen.StagesScreen.name) {
            StageInfoScreen(navController)
        }
    }.build()
}