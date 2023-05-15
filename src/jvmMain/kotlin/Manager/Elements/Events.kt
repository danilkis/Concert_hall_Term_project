package Manager.Elements

import Manager.Workers.Crew_data
import Manager.Workers.Event_data
import Workers.Data_types
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
//TODO: Добавить бар с полями