// MainScaffold.kt
package com.example.timefinder

import android.content.Intent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.timefinder.activities.BookingsActivity
import com.example.timefinder.activities.HomeActivity
import com.example.timefinder.activities.MainActivity
import com.example.timefinder.activities.ProfileActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(title: String, content: @Composable () -> Unit) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = {
//            CenterAlignedTopAppBar(
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    MaterialTheme.colorScheme.primary,
//                    titleContentColor = Color.White,
//                ),
//                title = {
//                    Text(
//                        title,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis,
//                        fontWeight = FontWeight.Bold
//                    )
//                },
//                scrollBehavior = scrollBehavior,
//            )
//        },
        bottomBar = {
            BottomNavigationBar()
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

@Composable
fun BottomNavigationBar() {
    val context = LocalContext.current

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home", modifier = Modifier.size(35.dp)) },
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
            icon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = "Zarezerwuj", modifier = Modifier.size(35.dp)) },
            label = { Text("Zarezerwuj") },
            selected = false,
            onClick = {
                context.startActivity(Intent(context, MainActivity::class.java))
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White
            )
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.List, contentDescription = "Rezerwacje", modifier = Modifier.size(35.dp)) },
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
            icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Profil", modifier = Modifier.size(35.dp)) },
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
