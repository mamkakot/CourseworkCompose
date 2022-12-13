package com.example.courseworkcompose.data.api

import com.example.courseworkcompose.models.chore.Chores
import com.example.courseworkcompose.models.room.Rooms
import com.example.courseworkcompose.models.user.Token
import com.example.courseworkcompose.models.user.User
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CleaningAppApiService {
    @GET("rooms/")
    suspend fun getRooms(): Response<Rooms>

    @GET("chores/")
    suspend fun getRoomsChores(@Query("room") roomId: Int): Response<Chores>

    @POST("auth/token/login/")
    suspend fun signInUser(@Body body: RequestBody): Token

    @POST("auth/users/")
    suspend fun registerUser(@Body body: RequestBody): User
}