package com.example.timefinder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableStateFlow<Result<Any>?>(null)
    val loginResult: StateFlow<Result<Any>?> = _loginResult

    @RequiresApi(Build.VERSION_CODES.O)
    fun login(email: String, password: String, userType: String) {
        viewModelScope.launch {
            try {
                val response = when (userType) {
                    "student" -> RetrofitInstance.api.loginStudent(LoginRequest(email, password))
                    "tutor" -> RetrofitInstance.api.loginTutor(LoginRequest(email, password))
                    else -> throw IllegalArgumentException("Invalid user type")
                }
                if (response.isSuccessful) {
                    _loginResult.value = Result.success(response.body()!!)
                } else {
                    _loginResult.value = Result.failure(Exception("Login failed"))
                }
            } catch (e: Exception) {
                _loginResult.value = Result.failure(e)
            }
        }
    }
}
