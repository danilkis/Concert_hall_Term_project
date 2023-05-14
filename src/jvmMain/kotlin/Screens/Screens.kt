package Screens

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
}
@Composable
fun CrewScreen(navController: NavController) //Экран с оборудованием на событиях
{
}
@Composable
fun PlanningScreen(navController: NavController) //Экран с сценами
{

}