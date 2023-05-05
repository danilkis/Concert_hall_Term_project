package Screens

import Elements.*
import Navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable

@Composable
fun EquipmentScreen(navController: NavController)
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
    Row(modifier = Modifier.fillMaxWidth())
    {
        EquipmentList()
    }
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