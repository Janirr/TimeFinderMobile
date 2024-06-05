// MainScaffold.kt
package com.example.timefinder

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
                title = {
                    Text(
                        "Strona główna",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                NavigationBarItem(
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Główna") },
                    selected = false,
                    onClick = {
                        context.startActivity(Intent(context, HomeActivity::class.java))
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.White
                    )
                )

                NavigationBarItem(
                    icon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = "Zarezerwuj") },
                    label = { Text("Zarezerwuj") },
                    selected = true,
                    onClick = {
                        context.startActivity(Intent(context, ReservationActivity::class.java))
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.White
                    )
                )
                NavigationBarItem(
                    icon = { Icon(imageVector = Icons.Default.List, contentDescription = "Rezerwacje") },
                    label = { Text("Rezerwacje") },
                    selected = false,
                    onClick = {
                        context.startActivity(Intent(context, BookingsActivity::class.java))
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.White
                    )
                )
                NavigationBarItem(
                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Profil") },
                    label = { Text("Profil") },
                    selected = false,
                    onClick = {
                        context.startActivity(Intent(context, ProfileActivity::class.java))
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White,
                        selectedTextColor = Color.White,
                        unselectedTextColor = Color.White
                    )
                )
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            content()
        }
    }
}
