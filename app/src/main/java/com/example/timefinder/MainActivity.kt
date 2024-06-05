package com.example.timefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timefinder.ui.theme.TimeFinderTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeFinderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DataScreen()
                }
            }
        }
    }
}

@Composable
fun DataScreen() {
    val coroutineScope = rememberCoroutineScope()
    var data by remember { mutableStateOf(listOf<Tutor>()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select a Tutor") }

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            RetrofitInstance.api.getData().enqueue(object : Callback<List<Tutor>> {
                override fun onResponse(
                    call: Call<List<Tutor>>,
                    response: Response<List<Tutor>>
                ) {
                    if (response.isSuccessful) {
                        data = response.body() ?: emptyList()
                    } else {
                        errorMessage = "Error: ${response.message()}"
                    }
                }

                override fun onFailure(call: Call<List<Tutor>>, t: Throwable) {
                    errorMessage = t.message
                }
            })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (errorMessage != null) {
            Text(text = "Error: $errorMessage")
        } else {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(onClick = { expanded = true }) {
                    Text(text = selectedOption)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    data.forEach { tutor ->
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                selectedOption = "${tutor.name} ${tutor.surname}"
                                expanded = false
                            },
                            text = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${tutor.name} ${tutor.surname}",
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
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
        DataScreen()
    }
}
