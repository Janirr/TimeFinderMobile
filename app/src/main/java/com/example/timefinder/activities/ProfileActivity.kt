package com.example.timefinder.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.timefinder.LoginService
import com.example.timefinder.MainScaffold
import com.example.timefinder.ui.theme.TimeFinderTheme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeFinderTheme {
                MainScaffold(title = "Profil") {
                    ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Text(text = LoginService.student.toString())
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    TimeFinderTheme {
        MainScaffold(title = "Profil") {
            ProfileScreen()
        }
    }
}
