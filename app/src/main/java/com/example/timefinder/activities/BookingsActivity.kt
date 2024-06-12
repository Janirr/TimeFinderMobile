package com.example.timefinder.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timefinder.MainScaffold
import com.example.timefinder.R
import com.example.timefinder.Reservation
import com.example.timefinder.UserService
import com.example.timefinder.ui.theme.TimeFinderTheme
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class BookingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeFinderTheme {
                MainScaffold {
                    BookingsScreen()
                }
            }
        }
    }
}

@Composable
fun BookingsScreen() {
    val reservationList = UserService.tutor?.reservationList ?: UserService.student?.reservationList
    val context = LocalContext.current
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.forLanguageTag("pl"))
    val dayFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale.forLanguageTag("pl"))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (reservationList.isNullOrEmpty()) {
            Text(
                text = "No reservations found.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        } else {
            LazyColumn {
                items(reservationList) { reservation ->
                    ReservationItem(reservation = reservation, onSendSMS = {
                        val startDateTime = LocalDateTime.ofInstant(
                            reservation.start.toInstant(),
                            ZoneId.systemDefault()
                        )
                        val day = startDateTime.format(dayFormatter)
                        val fromHour = startDateTime.format(timeFormatter)
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            val student = reservation.student
                            data = Uri.parse("sms:${student.phoneNumber}")
                            putExtra(
                                "sms_body",
                                "Cześć ${student.name}, czy zajęcia $day o $fromHour są aktualne?"
                            )
                        }
                        context.startActivity(intent)
                    }, onModifyTime = {
                        // Handle modify time action
                    })
                }
            }
        }
    }
}

@Composable
fun ReservationItem(
    reservation: Reservation,
    onSendSMS: () -> Unit,
    onModifyTime: () -> Unit
) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.forLanguageTag("pl"))
    val dayFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale.forLanguageTag("pl"))

    val startDateTime =
        LocalDateTime.ofInstant(reservation.start.toInstant(), ZoneId.systemDefault())
    val endDateTime = LocalDateTime.ofInstant(reservation.end.toInstant(), ZoneId.systemDefault())

    val day = startDateTime.format(dayFormatter)
    val fromHour = startDateTime.format(timeFormatter)
    val untilHour = endDateTime.format(timeFormatter)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Home")
                Text(text = " $day ", style = MaterialTheme.typography.bodyMedium)

                Icon(painter = painterResource(R.drawable.clock), contentDescription = "time")
                Text(text = " $fromHour ", style = MaterialTheme.typography.bodyMedium)
            }
            if (UserService.student == null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Student")
                    Text(
                        text = " ${reservation.student.name} ${reservation.student.surname}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onSendSMS,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Wyślij SMS", color = MaterialTheme.colorScheme.onPrimary)
                }
                Button(
                    onClick = onModifyTime,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(text = "Edytuj", color = MaterialTheme.colorScheme.onSecondary)
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookingsScreenPreview() {
    TimeFinderTheme {
        MainScaffold {
            BookingsScreen()
        }
    }
}