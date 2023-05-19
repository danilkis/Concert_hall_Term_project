package Tickets.Elements
import Tickets.Workers.Attending_worker
import Workers.Data_types
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AttendeeList() { //Лист с типами
    val Data = Attending_worker()
    LaunchedEffect(null) {
        Data.getAttendees()
    }
    val Atte = Attending_worker.People
    LazyColumn {
        items(Atte) { Atte ->
            TypeCard(Atte)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) //Карточки с типами
@Composable
fun TypeCard(person: Data_types.Companion.Attendee) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = person.Name + " " +person.Surname,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = "Билет: " + person.TicketId + " ID Посетителя: " + person.AttendeeId,
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun AddAttendee() {
    val Name = remember { mutableStateOf("") }
    val Surname = remember { mutableStateOf("") }
    val TicketId = remember { mutableStateOf("") }
    val ID = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    SnackbarHost(snackbarHostState.value)
    Row(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = Name.value,
            onValueChange = { Name.value = it },
            label = { Text("Имя") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Surname.value,
            onValueChange = { Surname.value = it },
            label = { Text("Фамилия") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = TicketId.value,
            onValueChange = { TicketId.value = it },
            label = { Text("ID билета") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = ID.value,
            onValueChange = { ID.value = it },
            label = { Text("ID посетителя") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                val Attend = Attending_worker(); Attend.AddAttendee(
                Data_types.Companion.Attendee(
                    Name.value,
                    Surname.value,
                    TicketId.value.toInt(),
                    ID.value.toInt(),
                )
            );Attending_worker().getAttendees()
                if(Attend.State)
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Добавленно")
                    }
                }
                else
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Что-то пошло не так, попробуйте еще раз")
                    }
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Добавить")
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                val Attend = Attending_worker(); Attend.RemoveAttendee(
                Data_types.Companion.Attendee(
                    Name.value,
                    Surname.value,
                    TicketId.value.toInt(),
                    ID.value.toInt(),
                )
            );Attending_worker().getAttendees()
                if(Attend.State)
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Удаленно")
                    }
                }
                else
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Что-то пошло не так, попробуйте еще раз")
                    }
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Удалить")
        }
    }
}