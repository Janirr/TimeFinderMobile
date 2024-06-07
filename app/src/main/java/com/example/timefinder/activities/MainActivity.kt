package com.example.timefinder.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timefinder.*
import com.example.timefinder.ui.theme.TimeFinderTheme
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeFinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScaffold(title = "Strona główna") {
                        DataScreen()
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DataScreen() {
    val coroutineScope = rememberCoroutineScope()
    val apiManager = remember { ApiManager() }
    var data by remember { mutableStateOf(listOf<Tutor>()) }
    var availableTimes by remember { mutableStateOf<Map<LocalDate, List<AvailableTime>>>(emptyMap()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var tutorExpanded by remember { mutableStateOf(false) }
    var timeExpanded by remember { mutableStateOf(false) }
    var selectedTutor by remember { mutableStateOf<Tutor?>(null) }
    var selectedTime by remember { mutableStateOf("60 minut") }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            apiManager.getTutors(
                onSuccess = { tutors -> data = tutors },
                onFailure = { error -> errorMessage = error }
            )
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            if (errorMessage != null) {
                Text(text = "Error: $errorMessage")
            } else {
                Card(
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Wybierz korepetytora",
                            color = Color.Black,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { tutorExpanded = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = selectedTutor?.let { "${it.name} ${it.surname}" } ?: "Wybierz korepetytora")
                                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                            }
                        }

                        DropdownMenu(
                            expanded = tutorExpanded,
                            onDismissRequest = { tutorExpanded = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            data.forEach { tutor ->
                                DropdownMenuItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        selectedTutor = tutor
                                        tutorExpanded = false
                                    },
                                    text = {
                                        Text(
                                            text = "${tutor.name} ${tutor.surname}",
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                        )
                                    })
                            }
                        }
                    }
                }

                Card(
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Ile czasu potrzebujesz?",
                            color = Color.Black,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { timeExpanded = true },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = selectedTime)
                                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                            }
                        }

                        DropdownMenu(
                            expanded = timeExpanded,
                            onDismissRequest = { timeExpanded = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            listOf("30 minut", "60 minut", "90 minut").forEach { time ->
                                DropdownMenuItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        selectedTime = time
                                        timeExpanded = false
                                    },
                                    text = {
                                        Text(
                                            text = time,
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center,
                                        )
                                    })
                            }
                        }
                    }
                }

                Button(
                    onClick = {
                        selectedTutor?.let { tutor ->
                            coroutineScope.launch {
                                apiManager.getFreeTime(
                                    tutorId = tutor.id,
                                    calendarId = tutor.calendarId,
                                    minutesForLesson = selectedTime.split(" ")[0].toInt(),
                                    onSuccess = { times -> availableTimes = times },
                                    onFailure = { error -> errorMessage = error }
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(text = "Pokaż dostępne terminy")
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (availableTimes.isNotEmpty()) {
                    Text(
                        text = "Dostępne terminy",
                        color = Color.Black,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        items(availableTimes.keys.toList()) { date ->
            Text(
                text = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("pl", "PL"))),
                style = MaterialTheme.typography.bodyLarge,
                color = if (selectedDate == date) MaterialTheme.colorScheme.primary else Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        selectedDate = date
                    }
            )

            if (selectedDate == date) {
                availableTimes[date]?.let { times ->
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        items(times) { time ->
                            Card(
                                shape = MaterialTheme.shapes.medium,
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .width(80.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = "${time.fromHour}",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DataScreenPreview() {
    TimeFinderTheme {
        MainScaffold(title = "Strona główna") {
            DataScreen()
        }
    }
}
