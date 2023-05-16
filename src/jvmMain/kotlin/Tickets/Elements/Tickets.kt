package Tickets.Elements

import Crew.Workers.Equipment_data
import Tickets.Workers.Ticket_worker
import Workers.Data_types
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
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
        Column (modifier = Modifier.padding(8.dp)) {
            Text(
                text = Ticket.EventName,
                style = MaterialTheme.typography.h5
            )
            Row() {
                AssistChip(
                    modifier = Modifier.padding(4.dp),
                    onClick = { /* Do something! */ },
                    label = { Text(Ticket.SectorName) },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Place,
                            contentDescription = "Localized description",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
                AssistChip(
                    modifier = Modifier.padding(4.dp),
                    onClick = { /* Do something! */ },
                    label = { Text(Ticket.DateOfPurchanse.toString()) },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.DateRange,
                            contentDescription = "Localized description",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
                AssistChip(
                    modifier = Modifier.padding(4.dp),
                    onClick = { /* Do something! */ },
                    label = { Text(Ticket.Price.toString()) }, //TODO: Иконка Money
                    leadingIcon = {
                        Icon(
                            Icons.Filled.DateRange,
                            contentDescription = "Localized description",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
                AssistChip(
                    modifier = Modifier.padding(4.dp),
                    onClick = { /* Do something! */ },
                    label = { Text(Ticket.TicketTypeName) }, //TODO: Иконка
                    leadingIcon = {
                        Icon(
                            Icons.Filled.DateRange,
                            contentDescription = "Localized description",
                            Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
            }
        }
    }
}