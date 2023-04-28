import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlin.properties.Delegates


import androidx.compose.ui.window.application


@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
        EquipmentTypesList()
    }
}

@Composable
fun EquipmentTypesList() {
    val equipmentTypes = DB().getEquipmentTypes()
    LazyColumn {
        items(equipmentTypes) { equipmentType ->
            EquipmentTypeCard(equipmentType)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentTypeCard(equipmentType: Equipment_types) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
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

fun main() = application {

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
