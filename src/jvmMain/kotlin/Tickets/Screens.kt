package Tickets

import Crew.Elements.*
import Manager.Elements.EventCrewList
import Navigation.NavController
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
        TicketList()
    }
}
@Composable
fun SectorsScreen(navController: NavController) //Экран с секторами
{

}
@Composable
fun EventEquipmentScreen(navController: NavController) //Экран с оборудованием на событиях
{

}
@Composable
fun StageInfoScreen(navController: NavController) //Экран с сценами
{

}