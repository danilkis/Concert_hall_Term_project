package Elements
import Workers.Data_types
import Workers.Equipment_data
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
fun EventEquipmentList() { //Лист с типами
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
fun EventEquipmentCard(equipmentType: Data_types.Companion.Equipment_types) {
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
fun AddEventEquipment() {
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
            );Equipment_data().getEquipmentTypes(); scope.launch {
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
                val Equipment = Equipment_data(); Equipment.RemoveType(
                Data_types.Companion.Equipment_types(
                    null,
                    text1.value,
                    text2.value
                )
            );Equipment_data().getEquipmentTypes();  scope.launch {
                snackbarHostState.value.showSnackbar("Удаленно")
            }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Удалить")
        }
    }
}