package Screens

import Elements.*
import Navigation.NavController
import Workers.Equipment_data
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun EquipmentScreen(navController: NavController)
{
    /*
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    SnackbarHost(snackbarHostState.value)
    */
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.weight(1f)) {
            AddType()
            TypesList()
        }
        Column(Modifier.weight(1f)) {
            AddEquipment()
            EquipmentList()
        }
    }
    /*
    if (Equipment_data().state == 2)
    {
        scope.launch {
            snackbarHostState.value.showSnackbar("Неверные данные!")
        }
        Equipment_data().state == 0
    }
    if (Equipment_data().state == 1)
    {
        scope.launch {
            snackbarHostState.value.showSnackbar("Выполненно")
        }
        Equipment_data().state == 0
    }
     */
}
@Composable
fun SectorsScreen(navController: NavController)
{
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.weight(1f)) {
            AddSector()
            SectorList()
        }
    }
}
@Composable
fun StagesScreen(navController: NavController)
{
    TestDialog()
}
@Composable
fun EventEquipmentScreen(navController: NavController)
{
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.weight(1f)) {
            AddType()
            TypesList()
        }
        Column(Modifier.weight(1f)) {
            AddEquipment()
            EquipmentList()
        }
    }
}