package Crew.Screens

import Crew.Elements.*
import Manager.Elements.EventCrewList
import Navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable

@Composable
fun EquipmentScreen(navController: NavController) //Экран с оборудованием
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
fun SectorsScreen(navController: NavController) //Экран с секторами
{
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.weight(1f)) {
            AddSector()
            SectorList()
        }
    }
}
@Composable
fun EventEquipmentScreen(navController: NavController) //Экран с оборудованием на событиях
{
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.weight(0.75f)) {
            Column{
                AddEventEquipment()
               EventEquipmentList()
            }
        }
        Column(Modifier.weight(1f)) {
            Column{
                EventCrewList()
            }
        }
    }
}
@Composable
fun StageInfoScreen(navController: NavController) //Экран с сценами //TODO: Посмотреть что с обновлением
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