package Screens

import Crew.Elements.AddSectorStages
import Crew.Elements.AddStage
import Crew.Elements.SectorStagesList
import Crew.Elements.StagesList
import Manager.Elements.*
import Navigation.NavController
import androidx.compose.foundation.layout.*
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

}