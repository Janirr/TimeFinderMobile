package com.example.timefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.timefinder.ui.theme.TimeFinderTheme

class BookingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeFinderTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    BookingsScreen()
                }
            }
        }
    }
}

@Composable
fun BookingsScreen() {
    // Your Home screen content
}

@Preview(showBackground = true)
@Composable
fun BookingsScreenPreview() {
    TimeFinderTheme {
        HomeScreen()
    }
}
