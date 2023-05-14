package Crew.Elements

import Crew.Workers.Data_types
import Crew.Workers.Stage_data
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.launch

@Composable
fun StagesList() { //Лист с типами
    var Data = Stage_data()
    LaunchedEffect(null) {
        Data.getStages()
    }
    var equipmentTypes = Stage_data.Stages
    LazyColumn {
        items(equipmentTypes) { equipmentType ->
            StageCard(equipmentType)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) //Карточки с типами
@Composable
fun StageCard(Stage: Data_types.Companion.Stage) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = Stage.Name + " ID: " + Stage.id,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "Вместимость " + Stage.StageCapacity.toString(),
                style = MaterialTheme.typography.body1
            )
        }
    }
}



@Composable
fun AddStage() {
    val ID = remember { mutableStateOf("") }
    val StageName = remember { mutableStateOf("") }
    val StageCapacity = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    SnackbarHost(snackbarHostState.value)
    Row(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = ID.value,
            onValueChange = { ID.value = it },
            label = { Text("ID") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = StageName.value,
            onValueChange = { StageName.value = it },
            label = { Text("Название сцены") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = StageCapacity.value,
            onValueChange = { StageCapacity.value = it },
            label = { Text("Вместимость") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                val Stages = Stage_data(); Stages.AddStage(
                Data_types.Companion.Stage(
                    ID.value.toInt(),
                    StageCapacity.value.toInt(),
                    StageName.value
                )
            );Stage_data().getStages(); scope.launch {
                snackbarHostState.value.showSnackbar("Добавленно")
            }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Добавить")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                val Stage = Stage_data(); Stage.RemoveStage(
                Data_types.Companion.Stage(
                    ID.value.toInt(),
                    StageCapacity.value.toInt(),
                    StageName.value
                )
            );Stage_data().getStages();  scope.launch {
                snackbarHostState.value.showSnackbar("Удаленно")
            }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Удалить")
        }
    }
}

