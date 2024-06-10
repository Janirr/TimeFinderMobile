package com.example.timefinder

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

interface ApiService {
    @GET("tutors")
    fun getTutors(): Call<List<Tutor>>

    @GET("/reservations/tutor/{tutorId}/calendar/{calendarId}/{minutesForLesson}")
    fun getFreeTime(
        @Path("tutorId") tutorId: Int,
        @Path("calendarId") calendarId: String,
        @Path("minutesForLesson") minutesForLesson: Int
    ): Call<HashMap<LocalDate, List<AvailableTime>>>

    @POST("/login/student")
    suspend fun loginStudent(@Body request: LoginRequest): Response<Student>

    @POST("/login/tutor")
    suspend fun loginTutor(@Body request: LoginRequest): Response<Tutor>
}

data class Student(
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val phoneNumber: String,
    val reservationList: List<Reservation>
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AvailableTime(
    @SerializedName("fromHour") val fromHour: LocalTime,
    @SerializedName("untilHour") val untilHour: LocalTime
)

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
