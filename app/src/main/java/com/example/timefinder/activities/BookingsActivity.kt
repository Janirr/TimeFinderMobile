package com.example.timefinder.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timefinder.ui.theme.TimeFinderTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import com.example.timefinder.MainScaffold
import com.example.timefinder.Reservation
import com.example.timefinder.UserService
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class BookingsActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeFinderTheme {
                MainScaffold(title = "Rezerwacje") {
                    BookingsScreen()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingsScreen() {
    val reservationList = UserService.tutor?.reservationList ?: UserService.student?.reservationList
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
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
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse("sms:999888777")
                            putExtra("sms_body", "Your reservation")
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReservationItem(
    reservation: Reservation,
    onSendSMS: () -> Unit,
    onModifyTime: () -> Unit
) {
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.forLanguageTag("pl"))
    val dayFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.forLanguageTag("pl"))

    val startDateTime = LocalDateTime.ofInstant(reservation.start.toInstant(), ZoneId.systemDefault())
    val endDateTime = LocalDateTime.ofInstant(reservation.end.toInstant(), ZoneId.systemDefault())

    val day = startDateTime.format(dayFormatter)
    val fromHour = startDateTime.format(timeFormatter)
    val untilHour = endDateTime.format(timeFormatter)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text(text = "Dzień: $day ", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Czas: $fromHour - $untilHour", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = onSendSMS, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Text(text = "Send SMS", color = MaterialTheme.colorScheme.onPrimary)
                }
                Button(onClick = onModifyTime, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)) {
                    Text(text = "Modify Time", color = MaterialTheme.colorScheme.onSecondary)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun BookingsScreenPreview() {
    TimeFinderTheme {
        MainScaffold(title = "Rezerwacje") {
            BookingsScreen()
        }
    }
}
