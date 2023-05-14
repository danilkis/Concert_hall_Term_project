package Manager.Elements

import Workers.Data_types
import Manager.Workers.EventCrew_data
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
            LazyRow {
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


