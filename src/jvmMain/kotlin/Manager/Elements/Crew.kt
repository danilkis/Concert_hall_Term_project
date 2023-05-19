package Manager.Elements
import Manager.Workers.Crew_data
import Workers.Data_types
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun CrewList() { //Лист с типами
    val Data = Crew_data()
    LaunchedEffect(null) {
        Data.getCrew()
    }
    val Crew = Crew_data.Crew
    LazyColumn(modifier = Modifier
        .fillMaxWidth()) {
        items(Crew) { Crew ->
            CrewCard(Crew)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class) //Карточки с типами
@Composable
fun CrewCard(Crew: Data_types.Companion.Crew) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column ()
            {
                Text(
                    text = Crew.Full_name,
                    style = MaterialTheme.typography.h5
                )
                Row()
                {
                    AssistChip(
                        modifier = Modifier.padding(4.dp),
                        onClick = { /* Do something! */ },
                        label = { Text(Crew.Email) },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Email,
                                contentDescription = "Localized description",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    )
                    AssistChip(
                        modifier = Modifier.padding(4.dp),
                        onClick = { /* Do something! */ },
                        label = { Text(Crew.Phone) },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Phone,
                                contentDescription = "Localized description",
                                Modifier.size(AssistChipDefaults.IconSize)
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Column {
                Text(
                    text = "Должность",
                    style = MaterialTheme.typography.h5
                )
                Text(
                text = Crew.CrewType,
                style = MaterialTheme.typography.body1
            )
                Text(
                    text = Crew.Sphere,
                    style = MaterialTheme.typography.body1
                )}


        }
    }
}
@Composable
fun AddCrew() {
    val Surname = remember { mutableStateOf("") }
    val Name = remember { mutableStateOf("") }
    val ThirdName = remember { mutableStateOf("") }
    val Phone = remember { mutableStateOf("") }
    val Email = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { mutableStateOf(SnackbarHostState()) }
    SnackbarHost(snackbarHostState.value)
    Row(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = Surname.value,
            onValueChange = { Surname.value = it },
            label = { Text("Фамилия") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = Name.value,
            onValueChange = { Name.value = it },
            label = { Text("Имя") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = ThirdName.value,
            onValueChange = { ThirdName.value = it },
            label = { Text("Отчество") },
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
            label = { Text("Почта") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        var expanded1 by remember { mutableStateOf(false) }
        val suggestions1 = Crew_data.Crew.distinctBy { it.Sphere }
        var selectedText1 by remember { mutableStateOf("") }

        val icon1 = if (expanded1)
            Icons.Filled.ArrowForward //it requires androidx.compose.material:material-icons-extended
        else
            Icons.Filled.ArrowDropDown
        OutlinedTextField(
            value = selectedText1,
            modifier = Modifier.weight(2f),
            onValueChange = { selectedText1 = it },
            label = { Text("Должность") },
            trailingIcon = {
                Icon(icon1, "contentDescription",
                    Modifier.clickable { expanded1 = !expanded1 })
            }
        )
        DropdownMenu(
            expanded = expanded1,
            modifier = Modifier.weight(2f),
            onDismissRequest = { expanded1 = false })
        {
            suggestions1.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText1 = label.CrewType
                }) {
                    Text(text = label.CrewType)
                }
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                val Cr = Crew_data(); Cr.AddCrew(
                Data_types.Companion.CrewAdd(
                    Name.value,
                    Surname.value,
                    ThirdName.value,
                    Phone.value,
                    Email.value,
                    selectedText1
                )
            );Crew_data().getCrew()
                if (Cr.State) {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Добавленно")
                    }
                } else {
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
                val Cr = Crew_data(); Cr.RemoveCrew(
                Data_types.Companion.CrewAdd(
                    Name.value,
                    Surname.value,
                    ThirdName.value,
                    Phone.value,
                    Email.value,
                    selectedText1
                )
            );Crew_data().getCrew()
                if (Cr.State) {
                    scope.launch {
                        snackbarHostState.value.showSnackbar("Удаленно")
                    }
                } else {
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
