package com.example.courseworkcompose.data.api

import android.util.Log
import com.example.courseworkcompose.models.chore.ChoreItem
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

    @POST("chores/")
    suspend fun postChore(@Body body: RequestBody): Response<ChoreItem>

    @PUT("chores/{choreId}/")
    suspend fun updateChore(@Path("choreId") choreId: Int, @Body body: RequestBody): Response<ChoreItem>

    @POST("auth/token/login/")
    suspend fun signInUser(@Body body: RequestBody): Response<Token>

    @POST("auth/users/")
    suspend fun registerUser(@Body body: RequestBody): Response<User>

    @GET("chores/{chore}")
    suspend fun getChore(@Path("chore") choreId: Int): Response<ChoreItem>
}