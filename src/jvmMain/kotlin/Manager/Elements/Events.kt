package Manager.Elements

import Manager.Workers.Crew_data
import Manager.Workers.Event_data
import Workers.Data_types
import androidx.compose.material3.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EventList() { //Лист с типами
    var Data = Event_data()
    LaunchedEffect(null) {
        Data.getEvents()
    }
    var Event = Event_data.Event
    LazyColumn(modifier = Modifier
        .fillMaxWidth()) {
        items(Event) { Event ->
            EventCard(Event)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class) //Карточки с типами
@Composable
fun EventCard(Event: Data_types.Companion.Events) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column ()
            {
                Text(
                    text = Event.EventName,
                    style = MaterialTheme.typography.h5
                )
                Row() //TODO: Пофиксить верстку
                {
                    AssistChip(
                        modifier = Modifier.padding(4.dp),
                        onClick = { /* Do something! */ },
                        label = { Text(Event.ArtistName) },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Person,
                                contentDescription = "Localized description",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    )
                    AssistChip(
                        modifier = Modifier.padding(4.dp),
                        onClick = { /* Do something! */ },
                        label = { Text(Event.Stage) },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Place,
                                contentDescription = "Localized description",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Column {
                Text(
                    text = "Начало: "+ Event.Start.toLocalDateTime().toString(),
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "Конец: "+ Event.End.toLocalDateTime().toString(),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}
fun stringToTimestamp(dateString: String): Timestamp {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val parsedDate = dateFormat.parse(dateString)
    val timestamp = Timestamp(parsedDate.time)
    return timestamp
}
@Composable
fun AddEvent() {
    val Event_name = remember { mutableStateOf("") }
    val Start = remember { mutableStateOf("") }
    val End = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    SnackbarHost(snackbarHostState.value)
    Row(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = Event_name.value,
            onValueChange = { Event_name.value = it },
            label = { Text("Название события") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Start.value,
            onValueChange = { Start.value = it },
            label = { Text("Начало") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = End.value,
            onValueChange = { End.value = it },
            label = { Text("Конец") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        var expanded1 by remember { mutableStateOf(false) }
        val suggestions1 = Event_data.Event.distinctBy { it.Stage }
        var selectedText1 by remember { mutableStateOf("") }

        val icon1 = if (expanded1)
            Icons.Filled.ArrowForward //it requires androidx.compose.material:material-icons-extended
        else
            Icons.Filled.ArrowDropDown
        OutlinedTextField(
            value = selectedText1,
            modifier = Modifier.weight(2f),
            onValueChange = { selectedText1 = it },
            label = { Text("Сцена") },
            trailingIcon = {
                Icon(icon1, "contentDescription",
                    Modifier.clickable { expanded1 = !expanded1 })
            }
        )
        DropdownMenu(
            expanded = expanded1,
            modifier = Modifier.weight(2f),
            onDismissRequest = { expanded1 = false })
        {
            suggestions1.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText1 = label.Stage
                }) {
                    Text(text = label.Stage)
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        var expanded2 by remember { mutableStateOf(false) }
        val suggestions2 = Event_data.Event.distinctBy { it.ArtistName }
        var selectedText2 by remember { mutableStateOf("") }

        val icon2 = if (expanded2)
            Icons.Filled.ArrowForward //it requires androidx.compose.material:material-icons-extended
        else
            Icons.Filled.ArrowDropDown
        OutlinedTextField(
            value = selectedText2,
            modifier = Modifier.weight(2f),
            onValueChange = { selectedText2 = it },
            label = { Text("Артист") },
            trailingIcon = {
                Icon(icon2, "contentDescription",
                    Modifier.clickable { expanded2 = !expanded2 })
            }
        )
        DropdownMenu(
            expanded = expanded2,
            modifier = Modifier.weight(2f),
            onDismissRequest = { expanded2 = false })
        {
            suggestions2.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText2 = label.ArtistName
                }) {
                    Text(text = label.ArtistName)
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                val Ev = Event_data(); Ev.AddEvent(
                Data_types.Companion.Events(
                    Event_name.value,
                    stringToTimestamp(Start.value),
                    stringToTimestamp(End.value),
                    selectedText1,
                    selectedText2
                )
            );Event_data().getEvents();
                if (!Ev.State) {
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
            Text("Добавить")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                val Ev = Event_data(); Ev.RemoveEvent(
                Data_types.Companion.Events(
                    Event_name.value,
                    stringToTimestamp(Start.value),
                    stringToTimestamp(End.value),
                    selectedText1,
                    selectedText2
                )
            );Event_data().getEvents();
                if (!Ev.State) {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Удаленно")
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
