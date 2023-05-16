package Tickets.Elements

import Crew.Workers.Equipment_data
import Tickets.Workers.Ticket_worker
import Workers.Data_types
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TicketList() { //Лист с типами
    var Data = Ticket_worker()
    LaunchedEffect(null) {
        Data.getTickets()
    }
    var Tickets = Ticket_worker.Tickets
    LazyColumn {
        items(Tickets) { Tickets -> //TODO: Тестирование
            TicketCard(Tickets)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) //Карточки с типами
@Composable
fun TicketCard(Ticket: Data_types.Companion.Ticket) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = Ticket.EventName,
                style = MaterialTheme.typography.h5
            )
            Row{
                Text(
                    text = "Купленно: " + Ticket.DateOfPurchanse.toString(),
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Использованно: " + Ticket.Used.toString(),
                    style = MaterialTheme.typography.body1
                )
            }
            Row{
                Text(
                    text = "Сектор: " + Ticket.SectorName,
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Тип билета: " + Ticket.TicketTypeName,
                    style = MaterialTheme.typography.body1
                )
            }

        }
    }
}