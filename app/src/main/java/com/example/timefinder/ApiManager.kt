package com.example.timefinder

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.util.HashMap

class ApiManager {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getTutors(
        onSuccess: (List<Tutor>) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            RetrofitInstance.api.getTutors().enqueue(object : Callback<List<Tutor>> {
                override fun onResponse(
                    call: Call<List<Tutor>>,
                    response: Response<List<Tutor>>
                ) {
                    if (response.isSuccessful) {
                        onSuccess(response.body() ?: emptyList())
                    } else {
                        onFailure("Error: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<List<Tutor>>, t: Throwable) {
                    onFailure(t.message)
                }
            })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getFreeTime(
        tutorId: Int,
        calendarId: String,
        minutesForLesson: Int,
        onSuccess: (Map<LocalDate, List<AvailableTime>>) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            RetrofitInstance.api.getFreeTime(tutorId, calendarId, minutesForLesson)
                .enqueue(object : Callback<HashMap<LocalDate, List<AvailableTime>>> {
                    override fun onResponse(
                        call: Call<HashMap<LocalDate, List<AvailableTime>>>,
                        response: Response<HashMap<LocalDate, List<AvailableTime>>>
                    ) {
                        if (response.isSuccessful) {
                            onSuccess(response.body() ?: emptyMap())
                        } else {
                            onFailure("Error: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<HashMap<LocalDate, List<AvailableTime>>>, t: Throwable) {
                        onFailure(t.message)
                    }
                })
        }
    }
}
