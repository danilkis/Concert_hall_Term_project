package Tickets.Elements

import Workers.Data_types
import Crew.Workers.Equipment_data
import Crew.Workers.Sector_data
import Tickets.Workers.TicketTypes_worker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.launch

@Composable
fun TicketTypesList() { //Лист с типами
    val Data = TicketTypes_worker()
    LaunchedEffect(null) {
        Data.getTT()
    }
    val TT = TicketTypes_worker.TicketTypes
    LazyColumn {
        items(TT) { TTs ->
            TTCard(TTs)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) //Карточки с типами
@Composable
fun TTCard(TT: Data_types.Companion.TicketTypes) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = TT.Name + " ID: " + TT.TicketTypeId,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "Сектор: " + TT.SectorId.toString(),
                style = MaterialTheme.typography.body1
            )
        }
    }
}
@Composable
fun AddTTControl() {
    val Data = Sector_data()
    LaunchedEffect(null) {
        Data.getSectors()
    }
    val TTID = remember { mutableStateOf("") }
    val Name = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    SnackbarHost(snackbarHostState.value)
    Row(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = TTID.value,
            onValueChange = { TTID.value = it },
            label = { Text("ID типа") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Name.value,
            onValueChange = { Name.value = it },
            label = { Text("Имя типа") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        var expanded by remember { mutableStateOf(false) }
        val suggestions = Sector_data.Sector
        var selectedText by remember { mutableStateOf("") }

        val icon = if (expanded)
            Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
        else
            Icons.Filled.ArrowDropDown
        OutlinedTextField(
            value = selectedText,
            modifier = Modifier.weight(1f),
            onValueChange = { selectedText = it },
            label = { Text("Сектор") },
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
                    selectedText = label.id.toString()
                }) {
                    Text(text = label.Name)
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                val TT = TicketTypes_worker(); TT.AddTT(
                Data_types.Companion.TicketTypes(
                    Name.value,
                    selectedText.toInt(),
                    TTID.value.toInt()
                )
            );Equipment_data().getEquipmentTypes()
                if(!TT.State)
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
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Добавить")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                val TT = TicketTypes_worker(); TT.RemoveTT(
                Data_types.Companion.TicketTypes(
                    Name.value,
                    selectedText.toInt(),
                    TTID.value.toInt()
                )
            );Equipment_data().getEquipmentTypes()
                if(!TT.State)
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Удаленно")
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
    }
}