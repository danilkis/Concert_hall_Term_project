package Manager.Elements

import Workers.Data_types
import Crew.Workers.Equipment_data
import Manager.Workers.Artist_data
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
fun ArtistList() { //Лист с типами
    var Data = Artist_data()
    LaunchedEffect(null) {
        Data.getArtists()
    }
    var artists = Artist_data.Artists
    LazyColumn {
        items(artists) { artists ->
            ArtistCard(artists)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) //Карточки с типами
@Composable
fun ArtistCard(Artist: Data_types.Companion.Artists) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = Artist.Name + " ID: " + Artist.id,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = Artist.Manager_name,
                style = MaterialTheme.typography.body1
            )
            Column {
                Text(
                text = Artist.Manager_phone,
                style = MaterialTheme.typography.body1
            )
                Text(
                    text = Artist.Manager_email,
                    style = MaterialTheme.typography.body1
                )}
        }
    }
}
@Composable
fun AddArtist() {
    val Artist = remember { mutableStateOf("") }
    val Manager = remember { mutableStateOf("") }
    val Phone = remember { mutableStateOf("") }
    val Email = remember { mutableStateOf("") }
    val Id = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    SnackbarHost(snackbarHostState.value)
    Row(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = Id.value,
            onValueChange = { Id.value = it },
            label = { Text("ID") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Artist.value,
            onValueChange = { Artist.value = it },
            label = { Text("Исполнитель") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Manager.value,
            onValueChange = { Manager.value = it },
            label = { Text("Имя менеджера") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Phone.value,
            onValueChange = { Phone.value = it },
            label = { Text("Телефон") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Email.value,
            onValueChange = { Email.value = it },
            label = { Text("Эл.почта") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                val Artists = Artist_data(); Artists.AddArtist(
                Data_types.Companion.Artists(
                    Id.value.toInt(),
                    Artist.value,
                    Manager.value,
                    Phone.value,
                    Email.value
                )
            );Artist_data().getArtists();
                if(!Artists.State)
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Добавленно")
                    }
                }
                else
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Ошибка! Проверьте точность данных")
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
                val Artists = Artist_data(); Artists.RemoveArtist(
                Data_types.Companion.Artists(
                    Id.value.toInt(),
                    Artist.value,
                    Manager.value,
                    Phone.value,
                    Email.value
                )
            );Artist_data().getArtists();
                if(!Artists.State)
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Удаленно")
                    }
                }
                else
                {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Ошибка! Проверьте точность данных")
                    }
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Удалить")
        }
    }
}
