package Crew.Elements

import Workers.Data_types
import Crew.Workers.Equipment_data
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import kotlinx.coroutines.launch

@Composable
fun TypesList() { //Лист с типами
    var Data = Equipment_data()
    LaunchedEffect(null) {
        Data.getEquipmentTypes()
    }
    var equipmentTypes = Equipment_data.Eq_types
    LazyColumn {
        items(equipmentTypes) { equipmentType ->
            TypeCard(equipmentType)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) //Карточки с типами
@Composable
fun TypeCard(equipmentType: Data_types.Companion.Equipment_types) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = equipmentType.Type,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = equipmentType.Subtype,
                style = MaterialTheme.typography.body1
            )
        }
    }
}
@Composable
fun AddType() {
    val text1 = remember { mutableStateOf("") }
    val text2 = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    SnackbarHost(snackbarHostState.value)
    Row(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = text1.value,
            onValueChange = { text1.value = it },
            label = { Text("Тип") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = text2.value,
            onValueChange = { text2.value = it },
            label = { Text("Подтип") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                val Equipment = Equipment_data(); Equipment.AddType(
                Data_types.Companion.Equipment_types(
                    null,
                    text1.value,
                    text2.value
                )
            );Equipment_data().getEquipmentTypes();
                if(!Equipment.State)
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
                val Equipment = Equipment_data(); Equipment.RemoveType(
                Data_types.Companion.Equipment_types(
                    null,
                    text1.value,
                    text2.value
                )
            );Equipment_data().getEquipmentTypes();
                if(!Equipment.State)
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

@Composable
fun EquipmentList() { //Лист с типами
    var Data = Equipment_data()
    LaunchedEffect(null) {
        Data.getEquipmentPlain()
    }
    var equipmentList = Equipment_data.equipment
    LazyColumn {
        items(equipmentList) { equipment ->
            EqupmentCard(equipment)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EqupmentCard(equipment: Data_types.Companion.Equipment) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = equipment.Manufacturer,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "В наличии: " + equipment.Stock,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "ID: " + equipment.id,
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "Тип: " + equipment.EquipmentTypeId,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun AddEquipment() {
    val Id = remember { mutableStateOf("") }
    val Manufacturer = remember { mutableStateOf("") }
    val Stock = remember { mutableStateOf("") }
    val EqTypeId = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    SnackbarHost(snackbarHostState.value)
    Row(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = Id.value,
            onValueChange = { Id.value = it },
            label = { Text("ID") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        var expanded1 by remember { mutableStateOf(false) }
        val suggestions1 = Equipment_data.equipment.distinctBy { it.Manufacturer }
        var selectedText1 by remember { mutableStateOf("") }

        val icon1 = if (expanded1)
            Icons.Filled.ArrowForward //it requires androidx.compose.material:material-icons-extended
        else
            Icons.Filled.ArrowDropDown
        OutlinedTextField(
            value = selectedText1,
            modifier = Modifier.weight(1f),
            onValueChange = { selectedText1 = it },
            label = { Text("Марка") },
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
                    selectedText1 = label.Manufacturer
                }) {
                    Text(text = label.Manufacturer)
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Stock.value,
            onValueChange = { Stock.value = it },
            label = { Text("Наличие") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        var expanded by remember { mutableStateOf(false) }
        val suggestions = Equipment_data.Eq_types
        var selectedText by remember { mutableStateOf("") }

        val icon = if (expanded)
            Icons.Filled.ArrowForward //it requires androidx.compose.material:material-icons-extended
        else
            Icons.Filled.ArrowDropDown
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
                    selectedText = label.id.toString()
                }) {
                    Text(text = label.Type + " " + label.Subtype)
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                var Equipment = Equipment_data(); Equipment.AddEquipment(
                    Data_types.Companion.Equipment(
                        Id.value.toInt(),
                        selectedText1,
                        Stock.value.toInt(),
                        selectedText.toInt()
                    )
                ); Equipment_data().getEquipmentPlain();
                if(!Equipment.State)
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
                val Equipment = Equipment_data(); Equipment.RemoveEquipment(
                Data_types.Companion.Equipment(
                    Id.value.toInt(),
                    selectedText1,
                    Stock.value.toInt(),
                    selectedText.toInt()
                )
            );Equipment.getEquipmentPlain();
                if(!Equipment.State)
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
