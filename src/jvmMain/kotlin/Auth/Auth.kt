package Auth

import Crew.Navigation.Screen
import Navigation.rememberNavController
import Crew.Navigation.CustomNavigationHost
import Navigation.CustomNavigationHostGlobal
import Workers.DB
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen()
{
    val screens = Screen.values().toList()
    val navController by rememberNavController(Navigation.Screen.AuthScreen.name)
    val currentScreen by remember {
        navController.currentScreen
    }
        Column( modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        val Login = remember { mutableStateOf("") }
        val Password = remember { mutableStateOf("") }
        CustomNavigationHostGlobal(navController = navController)
            OutlinedTextField(
                value = Login.value,
                onValueChange = { Login.value = it },
                label = { Text("Логин") }
            )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Password.value,
            onValueChange = { Password.value = it },
            label = { Text("Пароль") }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(onClick = {val Database = DB(); Database.user_glob = Login.value; Database.password_glob = Password.value; Database.establishPostgreSQLConnection(Database.user_glob,Database.password_glob);navController.navigate(Navigation.Screen.CrewScreen.name) },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Войти")
        }
    }
}