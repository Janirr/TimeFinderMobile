package com.example.timefinder

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

    @GET("tutors/{tutorId}/pricing")
    fun getPricing(
        @Path("tutorId") tutorId: Int,
    ): Call<List<Pricing>>
}

data class Pricing(
    val id: Long,
    val level: String,
    val price: String
)

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
    val fromHour: LocalTime,
    val untilHour: LocalTime
)

data class Reservation(
    val id: String,
    val start: Date,
    val end: Date,
    val summary: String,
    val student: Student
)

data class Tutor(
    val id: Int,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val email: String,
    val calendarId: String,
    val subject: String,
    val reservationList: List<Reservation>
)