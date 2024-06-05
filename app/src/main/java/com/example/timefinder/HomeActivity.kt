package com.example.timefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.timefinder.ui.theme.TimeFinderTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeFinderTheme {
                MainScaffold {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    // Your Home screen content
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TimeFinderTheme {
        MainScaffold {
            HomeScreen()
        }
    }
}

// Repeat similar steps for ReservationActivity, BookingsActivity, ProfileActivity
