package com.example.timefinder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingsViewModel : ViewModel() {

    private val _reservations = MutableStateFlow<List<Reservation>>(emptyList())
    val reservations: StateFlow<List<Reservation>> = _reservations

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchReservations(email: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getReservations(email)
                _reservations.value = response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
