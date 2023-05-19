package Auth

import Crew.Navigation.Screen
import Navigation.rememberNavController
import Crew.Navigation.CustomNavigationHost
import Navigation.CustomNavigationHostGlobal
import Workers.DB
import androidx.compose.foundation.layout.*
import androidx.compose.material.SnackbarHostState

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.compose.ReplyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen() {
    ReplyTheme(false)
    {
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
        var commentsAlpha by remember { mutableStateOf(1f) }
        val screens = Screen.values().toList()
        val navController by rememberNavController(Navigation.Screen.AuthScreen.name)
        val currentScreen by remember { navController.currentScreen }
        val isVisible = remember { mutableStateOf(true) }
        androidx.compose.material.SnackbarHost(snackbarHostState.value)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomNavigationHostGlobal(navController = navController)

            val Login = remember { mutableStateOf("") }
            val Password = remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.alpha(if (isVisible.value) 1f else 0f),
                value = Login.value,
                onValueChange = { Login.value = it },
                label = { Text("Логин") }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                modifier = Modifier.alpha(if (isVisible.value) 1f else 0f),
                value = Password.value,
                onValueChange = { Password.value = it },
                label = { Text("Пароль") }
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Button(
                onClick = {
                    try{
                    val Database = DB()
                    DB.user_glob = Login.value
                    DB.password_glob = Password.value
                    Database.establishPostgreSQLConnection(DB.user_glob, DB.password_glob)
                    commentsAlpha = 0f
                    isVisible.value = false
                    if (Login.value == "crew_worker") //TODO: Добавить Try Catch
                    {
                        navController.navigate(Navigation.Screen.CrewScreen.name)
                    }
                    else if (Login.value == "hall_manager")
                    {
                        navController.navigate(Navigation.Screen.ManagerScreen.name)
                    }
                    else if (Login.value == "tickets_worker")
                    {
                        navController.navigate(Navigation.Screen.TicketScreen.name)
                    }
                    }
                    catch (ex: Exception)
                    {
                        scope.launch {
                            snackbarHostState.value.showSnackbar("Что-то пошло не так, попробуйте еще раз")
                        }
                    }
                },
                modifier = Modifier.padding(top = 8.dp).alpha(if (isVisible.value) 1f else 0f)
            ) {
                Text("Войти")
            }
        }
    }
}
