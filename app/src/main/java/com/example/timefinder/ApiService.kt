package com.example.timefinder

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import java.util.Date

interface ApiService {
    @GET("tutors")
    fun getData(): Call<List<Tutor>>
}
data class Reservation(
    @SerializedName("id") val id: String,
    @SerializedName("start") val start: Date,
    @SerializedName("end") val end: Date,
    @SerializedName("summary") val summary: String
)

data class Tutor(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("surname") val surname: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("calendarId") val calendarId: String,
    @SerializedName("subject") val subject: String,
    @SerializedName("reservationList") val reservationList: List<Reservation>
)