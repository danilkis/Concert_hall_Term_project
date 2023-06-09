package Crew.Elements

import Workers.Data_types
import Crew.Workers.Sector_data
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SectorList() { //Лист с типами
    val Data = Sector_data()
    LaunchedEffect(null) {
        Data.getSectors()
    }
    val sectors = Sector_data.Sector
    LazyColumn {
        items(sectors) { Sector ->
            SectorCard(Sector)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) //Карточки с типами
@Composable
fun SectorCard(SectorType: Data_types.Companion.Sector) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = SectorType.Name + " ID: " + SectorType.id.toString(),
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "Вместимость: " + SectorType.SeatsTotal.toString(),
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Начало: " + SectorType.SeatsStart.toString(),
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Конец: " + SectorType.SeatsEnd.toString(),
                style = MaterialTheme.typography.body1
            )
        }
    }
}
@Composable
fun AddSector() {
    val Name = remember { mutableStateOf("") }
    val SeatsTotal = remember { mutableStateOf("") }
    val SeatsStart = remember { mutableStateOf("") }
    val SeatsEnd = remember { mutableStateOf("") }
    val ID = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    androidx.compose.material.SnackbarHost(snackbarHostState.value)
    Row(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = ID.value,
            onValueChange = { ID.value = it },
            label = { Text("ID") },
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
        OutlinedTextField(
            value = SeatsTotal.value,
            onValueChange = { SeatsTotal.value = it },
            label = { Text("Вместимость") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = SeatsStart.value,
            onValueChange = { SeatsStart.value = it },
            label = { Text("Начало сектора") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = SeatsEnd.value,
            onValueChange = { SeatsEnd.value = it },
            label = { Text("Конец сектора") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {val Sector = Sector_data(); Sector.AddSector(
                Data_types.Companion.Sector(
                    ID.value.toInt(),
                    SeatsTotal.value.toInt(),
                    SeatsStart.value.toInt(),
                    SeatsEnd.value.toInt(),
                    Name.value
                )
            ); Sector_data().getSectors()
                if(Sector.State)
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
            ); Sector_data().getSectors()
                if(Sector.State)
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Удалено")
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