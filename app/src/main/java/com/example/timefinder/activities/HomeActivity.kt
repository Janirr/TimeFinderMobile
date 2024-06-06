// HomeActivity.kt
package com.example.timefinder.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.timefinder.MainScaffold
import com.example.timefinder.ui.theme.TimeFinderTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeFinderTheme {
                MainScaffold(title = "Główna") {
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
        MainScaffold(title = "Główna") {
            HomeScreen()
        }
    }
}
