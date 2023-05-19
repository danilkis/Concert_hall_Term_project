package Crew.Elements
import Workers.Data_types
import Crew.Workers.Equipment_data
import Crew.Workers.EventEquipment_data
import Crew.Workers.Stage_data
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
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
fun EventEquipmentList() { //Лист с карточками
    var Data = EventEquipment_data()
    LaunchedEffect(null) {
        Data.getEventEquipment()
    }
    var equipmentTypes = EventEquipment_data.EventEquipment
    LazyColumn {
        items(equipmentTypes) { equipmentType ->
            EventEquipmentCard(equipmentType)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) //Карточки с типами
@Composable
fun EventEquipmentCard(EventEquipment: Data_types.Companion.EventEquipment) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row{
            Column(
                modifier = Modifier.padding(8.dp).width(250.dp)
            ) {
                Text(
                    text = "Событие",
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = EventEquipment.EventName,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = EventEquipment.StageName,
                    style = MaterialTheme.typography.body1
                )
            }
            Column(
                modifier = Modifier.padding(8.dp).width(250.dp)
            ) {
                Text(
                    text = "Оборудование",
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = EventEquipment.Type,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = EventEquipment.Subtype,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = EventEquipment.Manufacturer,
                    style = MaterialTheme.typography.body1
                )
            }
            Column(
                modifier = Modifier.padding(8.dp).width(250.dp)
            ) {
                Text(
                    text = "Информация",
                    style = MaterialTheme.typography.h5
                )
                Text(
                    text = "ID: " + EventEquipment.EquipmentId.toString(),
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "В наличии: " + EventEquipment.Stock.toString(),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}
@Composable
fun AddEventEquipment() { //TODO: Проверить что с добавлением
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    LaunchedEffect(null) {
        Stage_data().getStages()
        Equipment_data().getEquipmentPlain()
    }
    val EquipmentId = remember { mutableStateOf("") }
    SnackbarHost(snackbarHostState.value)
    Row(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.padding(8.dp))
        var expanded1 by remember { mutableStateOf(false) }
        val suggestions1 = EventEquipment_data.EventEquipment.distinctBy { it.EventId }
        var selectedText1 by remember { mutableStateOf("") }

        val icon1 = if (expanded1)
            Icons.Filled.ArrowDropUp //it requires androidx.compose.material:material-icons-extended
        else
            Icons.Filled.ArrowDropDown
        OutlinedTextField(
            value = selectedText1,
            modifier = Modifier.weight(1f),
            onValueChange = { selectedText1 = it },
            label = { Text("Концерт") },
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
                    selectedText1 = label.EventId.toString()
                }) {
                    Text(text = label.EventName)
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = EquipmentId.value,
            onValueChange = { EquipmentId.value = it },
            label = { Text("ID оборудования") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                var eveq_dt = EventEquipment_data(); eveq_dt.AddEventEquipment(
                    Data_types.Companion.EventEquipment(
                        "",
                        "",
                        selectedText1.toInt(),
                        "",
                        "",
                        "",
                        0,
                        EquipmentId.value.toInt()
                    )
                ); EventEquipment_data().getEventEquipment();
                    if(!eveq_dt.State)
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
                var eveq_dt = EventEquipment_data(); eveq_dt.RemoveEventEquipment(
                    Data_types.Companion.EventEquipment(
                        "",
                        "",
                        selectedText1.toInt(),
                        "",
                        "",
                        "",
                        0,
                        EquipmentId.value.toInt()
                    )
                ); EventEquipment_data().getEventEquipment();
                if(!eveq_dt.State)
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