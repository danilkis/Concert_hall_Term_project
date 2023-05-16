package Screens

import Crew.Elements.*
import Manager.Elements.*
import Navigation.NavController
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable

@Composable
fun ArtistScreen(navController: NavController) //Экран с оборудованием
{
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.weight(1f)) {
            AddArtist()
            ArtistList()
        }
    }
}
@Composable
fun EventScreen(navController: NavController) //Экран с секторами
{
    Column()
    {
        AddEvent()
        EventList()
    }
}
@Composable
fun CrewScreen(navController: NavController) //Экран с оборудованием на событиях
{
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.weight(1f)) {
            Column{
                AddEventCrew()
                EventCrewList()
            }
        }
        Column(Modifier.weight(1.5f)) {
            Column{
                AddCrew()
                CrewList()
            }
        }
    }
}
@Composable
fun PlanningScreen(navController: NavController) //Экран с сценами
{
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.weight(1f)) {
            Text(
                text = "Сцены",
                style = MaterialTheme.typography.h5
            )
            Column{
                StagesList()
            }
        }
        Column(Modifier.weight(1.5f)) {
            Column{
                Text(
                    text = "Сектора",
                    style = MaterialTheme.typography.h5
                )
                SectorList()
            }
        }
        Column(Modifier.weight(1.5f)) {
            Column{
                Text(
                    text = "Сектора-Сцены",
                    style = MaterialTheme.typography.h5
                )
                SectorStagesList()
            }
        }
    }
}