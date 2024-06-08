package com.example.timefinder.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.timefinder.*

class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(viewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val context = LocalContext.current
    val loginResult by viewModel.loginResult.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("student") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            RadioButton(
                selected = userType == "student",
                onClick = { userType = "student" }
            )
            Text("Student")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = userType == "tutor",
                onClick = { userType = "tutor" }
            )
            Text("Tutor")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.login(email, password, userType) }) {
            Text("Login")
        }

        loginResult?.let { result ->
            result.onSuccess { student ->
                Text("Login successful", color = Color.Green)
                LaunchedEffect(Unit) {
                    LoginService.student = student as Student // for now only Student
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            }
            result.onFailure {
                Text("Login failed: ${it.message}", color = Color.Red)
            }
        }
    }
}
