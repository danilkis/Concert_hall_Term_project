import Navigation.*
import Screens.EquipmentScreen
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import Screens.EquipmentScreen
import Screens.CustomNavigationHost
import Screens.SectorsScreen
import Screens.StagesScreen
import Screens.EventEquipmentScreen
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    val screens = Screen.values().toList()
    val navController by rememberNavController(Screen.HomeScreen.name)
    val currentScreen by remember {
        navController.currentScreen
    }

    MaterialTheme {
        Scaffold(
                modifier = Modifier.fillMaxSize()
            ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(0.dp, 75.dp, 0.dp, 0.dp)
            ) {
                CustomNavigationHost(navController = navController)
            }
                NavigationBar(){
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
fun main() = application {

    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
