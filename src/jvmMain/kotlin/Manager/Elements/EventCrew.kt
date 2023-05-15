package Manager.Elements

import Crew.Workers.Equipment_data
import Manager.Workers.Artist_data
import Workers.Data_types
import Manager.Workers.EventCrew_data
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun EventCrewList() { //Лист с типами
    var Data = EventCrew_data()
    LaunchedEffect(null) {
        Data.getEventCrew()
    }
    var EventCrew = EventCrew_data.EventCrew
    LazyColumn(modifier = Modifier
        .fillMaxWidth()) {
        items(EventCrew) { Event ->
            EventCrewCard(Event)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class) //Карточки с типами
@Composable
fun EventCrewCard(Artist: Data_types.Companion.EventCrew) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = Artist.EventName,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "ID сцены:" + Artist.StageId.toString(),
                style = MaterialTheme.typography.body1
            )
            val names =Artist.Workers.toString()
                .trim('{', '}')
                .split(',')
                .map { it.trim() }
            LazyRow (){
                items(names) { item ->
                    AssistChip(
                        modifier = Modifier.padding(4.dp),
                        onClick = { /* Do something! */ },
                        label = { Text(item) },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Person,
                                contentDescription = "Localized description",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddEventCrew() {
    val Surname = remember { mutableStateOf("") }
    val Name = remember { mutableStateOf("") }
    val EventId = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    androidx.compose.material.SnackbarHost(snackbarHostState.value)
    Row(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = Surname.value,
            onValueChange = { Surname.value = it },
            label = { Text("Фамилия") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Name.value,
            onValueChange = { Name.value = it },
            label = { Text("Имя") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        var expanded1 by remember { mutableStateOf(false) }
        val suggestions1 = EventCrew_data.EventCrew.distinctBy { it.EventName }
        var selectedText1 by remember { mutableStateOf("") }

        val icon1 = if (expanded1)
            Icons.Filled.ArrowForward //it requires androidx.compose.material:material-icons-extended
        else
            Icons.Filled.ArrowDropDown
        OutlinedTextField(
            value = selectedText1,
            modifier = Modifier.weight(1f),
            onValueChange = { selectedText1 = it },
            label = { Text("Событие") },
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
            onClick = {
                val EvCr = EventCrew_data(); EvCr.AddEventCrew(
                Data_types.Companion.EventCrewAdd(
                    selectedText1,
                    Name.value,
                    Surname.value
                )
            );EventCrew_data().getEventCrew();
                if(!EvCr.State)
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Добавленно")
                    }
                }
                else
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Ошибка! Проверьте точность данных")
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
                val EvCr = EventCrew_data(); EvCr.AddEventCrew(
                Data_types.Companion.EventCrewAdd(
                    selectedText1,
                    Name.value.toString(),
                    Surname.value
                )
            );Artist_data().getArtists();
                if (!EvCr.State) {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Добавленно")
                    }
                } else {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Ошибка! Проверьте точность данных")
                    }
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Удалить")
        }
    }
}

