package Tickets.Elements

import Crew.Workers.Equipment_data
import Crew.Workers.Sector_data
import Tickets.Workers.Ticket_worker
import Workers.Data_types
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat

@Composable
fun TicketList() { //Лист с типами
    var Data = Ticket_worker()
    LaunchedEffect(null) {
        Data.getTickets()
    }
    var Tickets = Ticket_worker.Tickets
    LazyColumn {
        items(Tickets) { Tickets ->
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
            Row {
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
fun stringToTimestamp(dateString: String): Timestamp { //TODO: Unparcable date fix
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val parsedDate = dateFormat.parse(dateString)
    val timestamp = Timestamp(parsedDate.time)
    return timestamp
}
@Composable
fun AddTicket() {
    val Price = remember { mutableStateOf("") }
    val Date = remember { mutableStateOf("") }
    val Used = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    androidx.compose.material.SnackbarHost(snackbarHostState.value)
    Row(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = Price.value,
            onValueChange = { Price.value = it },
            label = { Text("Цена") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Date.value,
            onValueChange = { Date.value = it },
            label = { Text("Дата покупки") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(  //TODO: Сделать switch
            value = Used.value,
            onValueChange = { Used.value = it },
            label = { Text("SWITCH") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        var expanded by remember { mutableStateOf(false) }
        val suggestions = Ticket_worker.Tickets.distinctBy { it.TicketTypeName }
        var selectedText by remember { mutableStateOf("") }

        val icon = if (expanded)
            Icons.Filled.ArrowForward //it requires androidx.compose.material:material-icons-extended
        else
            Icons.Filled.ArrowDropDown
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = selectedText,
            modifier = Modifier.weight(1f),
            onValueChange = { selectedText = it },
            label = { Text("Тип") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )

        DropdownMenu(
            expanded = expanded,
            modifier = Modifier.weight(1f),
            onDismissRequest = { expanded = false })
        {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label.TicketTypeName
                }) {
                    Text(text = label.TicketTypeName)
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        var expanded1 by remember { mutableStateOf(false) }
        val suggestions1 = Ticket_worker.Tickets.distinctBy { it.EventName }
        var selectedText1 by remember { mutableStateOf("") }

        val icon1 = if (expanded1)
            Icons.Filled.ArrowForward //it requires androidx.compose.material:material-icons-extended
        else
            Icons.Filled.ArrowDropDown
        OutlinedTextField(
            value = selectedText1,
            modifier = Modifier.weight(1f),
            onValueChange = { selectedText1 = it },
            label = { Text("Мероприятие") },
            trailingIcon = {
                Icon(icon1, "contentDescription",
                    Modifier.clickable { expanded1 = !expanded1 })
            }
        )

        DropdownMenu(
            expanded = expanded1,
            modifier = Modifier.weight(1f),
            onDismissRequest = { expanded1 = false })
        {
            suggestions1.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText1 = label.EventName
                }) {
                    Text(text = label.EventName)
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {val Tickets = Ticket_worker(); Tickets.AddTicket(
                Data_types.Companion.Ticket(
                    Price.value.toInt(),
                    stringToTimestamp(Date.value),
                    Used.value.toBoolean(),
                    selectedText,
                    selectedText1,
                    ""
                )
            ); Sector_data().getSectors();
                if(!Tickets.State)
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Добавленно")
                    }
                }
                else
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Что-то пошло не так, попробуйте еще раз")
                    }
                } },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Добавить")
        }
        /*
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                val Sector = Sector_data(); Sector.RemoveSectors(
                Data_types.Companion.Sector(
                    ID.value.toInt(),
                    SeatsTotal.value.toInt(),
                    SeatsStart.value.toInt(),
                    SeatsEnd.value.toInt(),
                    Name.value
                )
            ); Sector_data().getSectors();
                if(!Sector.State)
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Удаллено")
                    }
                }
                else
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Что-то пошло не так, попробуйте еще раз")
                    }
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Удалить")
        }

         */
    }
}