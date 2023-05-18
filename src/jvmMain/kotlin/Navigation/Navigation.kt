package Navigation

import Crew.CrewWindow
import Crew.Navigation.Screen
import Crew.Screens.EquipmentScreen
import Crew.Screens.EventEquipmentScreen
import Crew.Screens.SectorsScreen
import Crew.Screens.StageInfoScreen
import Manager.ManagerScreen
import Tickets.TicketScreen
import Tickets.TicketWindow
import androidx.compose.runtime.Composable

@Composable
fun CustomNavigationHostGlobal(
    navController: NavController
) {
    NavigationHost(navController) {
        composable(Navigation.Screen.CrewScreen.name) {
            CrewWindow(navController)
        }

        composable(Navigation.Screen.ManagerScreen.name) {
            ManagerScreen(navController)
        }

        composable(Navigation.Screen.TicketScreen.name) {
            TicketWindow(navController)
        }
    }.build()
}