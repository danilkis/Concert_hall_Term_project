package Crew.Elements

import Workers.Data_types
import Crew.Workers.SectorStages_data
import Crew.Workers.Sector_data
import Crew.Workers.Stage_data
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SectorStagesList() { //Лист с типами
    var Data = SectorStages_data()
    LaunchedEffect(null) {
        Data.getStagesSectors()
    }
    var equipmentTypes = SectorStages_data.StagesSectors
    LazyColumn {
        items(equipmentTypes) { equipmentType ->
            SectorStageCard(equipmentType)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) //Карточки с типами
@Composable
fun SectorStageCard(StageSector: Data_types.Companion.SectorStages) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = StageSector.StageName + " ID: " + StageSector.StageId,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "Сектор: " + StageSector.SectorName + " ID: " + StageSector.SectorId.toString(),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun AddSectorStages() {
    LaunchedEffect(null) {
        Stage_data().getStages()
        Sector_data().getSectors()
        SectorStages_data().getStagesSectors()
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    androidx.compose.material.SnackbarHost(snackbarHostState.value)
    Row(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.padding(8.dp))
        var expanded1 by remember { mutableStateOf(false) }
        val suggestions1 = Stage_data.Stages.distinctBy { it.id }
        var selectedText1 by remember { mutableStateOf("") }

        val icon1 = if (expanded1)
            Icons.Filled.ArrowForward //it requires androidx.compose.material:material-icons-extended
        else
            Icons.Filled.ArrowDropDown
        OutlinedTextField(
            value = selectedText1,
            modifier = Modifier.weight(1f),
            onValueChange = { selectedText1 = it },
            label = { Text("Сцена") },
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
                    selectedText1 = label.id.toString()
                }) {
                    Text(text = label.Name)
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        var expanded by remember { mutableStateOf(false) }
        val suggestions = Sector_data.Sector
        var selectedText by remember { mutableStateOf("") }

        val icon = if (expanded)
            Icons.Filled.ArrowForward //it requires androidx.compose.material:material-icons-extended
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
                    Text(text = label.Name.toString())
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                var srcstg = SectorStages_data(); srcstg.AddStageSector(
                    Data_types.Companion.SectorStages(
                        selectedText1.toInt(),
                        " ",
                        selectedText.toInt(),
                        " "
                    )
                ); SectorStages_data().getStagesSectors();
                if(!srcstg.State)
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
                var stgsec = SectorStages_data(); stgsec.RemoveSectorStage(
                    Data_types.Companion.SectorStages(
                        selectedText1.toInt(),
                        " ",
                        selectedText.toInt(),
                        " "
                    )
                ); SectorStages_data().getStagesSectors();
                if(!stgsec.State)
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