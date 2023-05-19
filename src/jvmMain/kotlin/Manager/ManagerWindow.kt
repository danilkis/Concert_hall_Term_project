package Manager

import Crew.Navigation.CustomNavigationHost
import Manager.Navigation.CustomNavigationHostManager
import Manager.Navigation.Screen
import Navigation.NavController
import Navigation.rememberNavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compose.ReplyTheme

@Composable
fun ManagerScreen(navController: NavController) //
{
    val screens = Screen.values().toList()
    val navController by rememberNavController(Screen.ArtistScreen.name)
    val currentScreen by remember {
        navController.currentScreen
    }
    ReplyTheme (false) {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(0.dp, 75.dp, 0.dp, 0.dp)
            ) {
                CustomNavigationHostManager(navController = navController)
            }
            NavigationBar {
                screens.forEach {
                    NavigationBarItem(
                        selected = currentScreen == it.name,
                        icon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.label
                            )
                        },
                        label = {
                            Text(it.label)
                        },
                        alwaysShowLabel = false,
                        onClick = {
                            navController.navigate(it.name)
                        }
                    )
                }
            }
        }
    }
}
