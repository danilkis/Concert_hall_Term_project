package Tickets

import Crew.Elements.*
import Manager.Elements.EventCrewList
import Manager.Elements.EventList
import Navigation.NavController
import Tickets.Elements.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TicketScreen(navController: NavController) //Экран с оборудованием
{
    Row(modifier = Modifier.fillMaxWidth()) {
        Column (Modifier.weight(1f)){
            AddTicket()
            TicketList()  }
        Column (Modifier.weight(0.65f)){
            AddTTControl()
            TicketTypesList()  }
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
fun StageMiniInfo(navController: NavController) //TODO: Переписать без использования сторониих таблиц
{
    Row(modifier = Modifier.fillMaxWidth()) {
        Column (Modifier.weight(1f)){
            Text(
                text = "События",
                style = MaterialTheme.typography.h5
            )
            EventList()
        }
        Column (Modifier.weight(1f)){
            Text(
                text = "Сектора-сцены",
                style = MaterialTheme.typography.h5
            )
            SectorStagesList()
        }
        Column (Modifier.weight(0.7f)){
            Text(
                text = "Сцены",
                style = MaterialTheme.typography.h5
            )
            StagesList()
        }
    }
}