package com.example.timefinder.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timefinder.ApiManager
import com.example.timefinder.AvailableTime
import com.example.timefinder.MainScaffold
import com.example.timefinder.Tutor
import com.example.timefinder.ui.theme.TimeFinderTheme
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeFinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScaffold {
                        DataScreen()
                    }
                }
            }
        }
    }
}

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
    var showCalendar by remember { mutableStateOf(false) } // State to control the visibility of the calendar

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
                                    onSuccess = { times ->
                                        availableTimes = times
                                        showCalendar = true // Show calendar when the available times are loaded
                                    },
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
            }
        }

        // Conditionally display the calendar based on showCalendar state
        if (showCalendar) {
            item {
                CalendarView(
                    availableDates = availableTimes.keys.toList(),
                    selectedDate = selectedDate,
                    onDateSelected = { date ->
                        selectedDate = date
                    }
                )
            }
        }

        if (selectedDate != null && availableTimes[selectedDate] != null) {
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    items(availableTimes[selectedDate]!!) { time ->
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

@Composable
fun CalendarView(
    availableDates: List<LocalDate>,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val currentMonth = YearMonth.now()
    val firstDayOfMonth = currentMonth.atDay(1)
    val lastDayOfMonth = currentMonth.atEndOfMonth()
    val daysInMonth = firstDayOfMonth.dayOfWeek.value % 7
    val dateMap = availableDates.associateWith { true }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DayOfWeek.entries.forEach { dayOfWeek ->
                Text(
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.forLanguageTag("pl")),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        for (week in 0 until (daysInMonth + lastDayOfMonth.dayOfMonth) / 7 + 1) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (day in 0..6) {
                    val dayOfMonth = week * 7 + day - daysInMonth + 1
                    if (dayOfMonth > 0 && dayOfMonth <= lastDayOfMonth.dayOfMonth) {
                        val date = currentMonth.atDay(dayOfMonth)
                        val isAvailable = date in dateMap
                        Text(
                            text = dayOfMonth.toString(),
                            textAlign = TextAlign.Center,
                            color = when {
                                date == selectedDate -> MaterialTheme.colorScheme.primary
                                isAvailable -> Color.Black
                                else -> Color.Gray
                            },
                            fontWeight = if (date == selectedDate) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                                .clickable(enabled = isAvailable) { onDateSelected(date) }
                        )
                    } else {
                        Text(
                            text = "",
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DataScreenPreview() {
    TimeFinderTheme {
        MainScaffold {
            DataScreen()
        }
    }
}