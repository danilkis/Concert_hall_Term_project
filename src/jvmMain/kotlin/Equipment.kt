import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize

@Composable
    fun TypesList() { //Лист с типами
        val equipmentTypes = DB().getEquipmentTypes()
        LazyColumn {
            items(equipmentTypes) { equipmentType ->
                TypeCard(equipmentType)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class) //Карточки с типами
    @Composable
    fun TypeCard(equipmentType: Equipment_types) {
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
            onClick = {val Database = DB(); Database.AddType(Equipment_types(null, text1.value, text2.value)) },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Добавить")
        }
    }
}
    @Composable
    fun EquipmentList() {
        val equipment = DB().getEquipmentPlain()
        LazyColumn {
            items(equipment) { equipment ->
                EqupmentCard(equipment)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun EqupmentCard(equipment: Equipment) {
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
fun ChooseType()
{
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Item1","Item2","Item3")
    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero)}

    val icon = if (expanded)
        Icons.Filled.ArrowForward //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.ArrowDropDown
    OutlinedTextField(
        value = selectedText,
        onValueChange = { selectedText = it },
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                //This value is used to assign to the DropDown the same width
                textfieldSize = coordinates.size.toSize()
            },
        label = {Text("Label")},
        trailingIcon = {
            Icon(icon,"contentDescription",
                Modifier.clickable { expanded = !expanded })
        }
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .width(with(LocalDensity.current){textfieldSize.width.toDp()})
    ) {
        suggestions.forEach { label ->
            DropdownMenuItem(onClick = {
                selectedText = label
            }) {
                Text(text = label)
            }
        }
    }
}
@Composable
fun AddEquipment() {
    val Id = remember { mutableStateOf("") }
    val Manufacturer = remember { mutableStateOf("") }
    val Stock = remember { mutableStateOf("") }
    val EqTypeId = remember { mutableStateOf("") }

    Row(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = Id.value,
            onValueChange = { Id.value = it },
            label = { Text("ID") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Manufacturer.value,
            onValueChange = { Manufacturer.value = it },
            label = { Text("Производитель") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Stock.value,
            onValueChange = { Stock.value = it },
            label = { Text("Наличие (Штук)") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        ChooseType()
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {val Database = DB(); Database.AddEquipment(Equipment(Id.value.toInt(), Manufacturer.value, Stock.value.toInt(), EqTypeId.value.toInt())) },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Добавить")
        }
    }
}