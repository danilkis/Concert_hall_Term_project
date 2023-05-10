package Elements

import Workers.Data_types
import Workers.Equipment_data
import Workers.Sector_data
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SectorList() { //Лист с типами
    val Sectors = Sector_data().getSectors()
    LazyColumn {
        items(Sectors) { Sector ->
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

    Row(modifier = Modifier.padding(16.dp)) {
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
            onValueChange = { SeatsTotal.value = it },
            label = { Text("Начало сектора") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = SeatsEnd.value,
            onValueChange = { SeatsTotal.value = it },
            label = { Text("Конец сектора") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {val Sector = Sector_data(); Sector.AddType(
                Data_types.Companion.Sector(
                    null,
                    SeatsTotal.value.toInt(),
                    SeatsStart.value.toInt(),
                    SeatsEnd.value.toInt(),
                    Name.value
                )
            ) },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Добавить")
        }
    }
}
@Composable
fun SnackbarTest()
{

}