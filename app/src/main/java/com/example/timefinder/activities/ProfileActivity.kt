package com.example.timefinder.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.timefinder.UserService
import com.example.timefinder.MainScaffold
import com.example.timefinder.ui.theme.TimeFinderTheme
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeFinderTheme {
                MainScaffold {
                    ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        UserService.student?.let {
            Text(text = it.name)
        }

        UserService.tutor?.let {
            Text(text = it.name)
            Button(onClick = {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("sms:536806066")
                    putExtra("sms_body", "Your reservation")
                }
                context.startActivity(intent)
            }) {
                Text(text = "Send SMS")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    TimeFinderTheme {
        MainScaffold {
            ProfileScreen()
        }
    }
}
