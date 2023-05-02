import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.net.PasswordAuthentication

    @Composable
    fun AuthScreen()
    {
        Column{
            val Login = remember { mutableStateOf("") }
            val Password = remember { mutableStateOf("") }
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
            Button(onClick = {val Database = DB(); Database.establishPostgreSQLConnection(Login.value, Password.value) },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Войти")
            }
        }
    }