package Tickets

import Crew.Elements.*
import Manager.Elements.EventCrewList
import Navigation.NavController
import Tickets.Elements.AddAttendee
import Tickets.Elements.AddTicket
import Tickets.Elements.AttendeeList
import Tickets.Elements.TicketList
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TicketScreen(navController: NavController) //Экран с оборудованием
{
    Row(modifier = Modifier.fillMaxWidth()) {
        Column {
            AddTicket()
            TicketList()  }

    }
}
@Composable
fun AttendeesScreen(navController: NavController) //Экран с секторами
{
    Row(modifier = Modifier.fillMaxWidth()) {
        Column {
            AddAttendee()
            AttendeeList()  }
    }
}
@Composable
fun EventEquipmentScreen(navController: NavController) //Экран с оборудованием на событиях
{

}
@Composable
fun StageInfoScreen(navController: NavController) //Экран с сценами
{

}