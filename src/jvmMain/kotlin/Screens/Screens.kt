package Screens

import Elements.*
import Navigation.NavController
import Workers.Equipment_data
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
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
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.weight(1f)) {
            Column{
                AddEventEquipment()
               EventEquipmentList()
            }
        }
    }
}
@Composable
fun EventEquipmentScreen(navController: NavController)
{
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.weight(1f)) {
            Column{
                AddStage()
                StagesList()
            }
        }
        Column(Modifier.weight(1f)) {
            Column{
                AddSectorStages()
                SectorStagesList()
            }
        }
    }
}