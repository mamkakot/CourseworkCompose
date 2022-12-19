package com.example.courseworkcompose.data.repository

import android.util.Log
import com.example.courseworkcompose.data.api.CleaningAppApiService
import com.example.courseworkcompose.data.api.RetrofitInstance
import com.example.courseworkcompose.models.chore.ChoreItem
import com.example.courseworkcompose.models.chore.Chores
import com.example.courseworkcompose.models.room.Rooms
import com.example.courseworkcompose.models.user.Token
import com.example.courseworkcompose.models.user.User
import dagger.hilt.android.scopes.ActivityScoped
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject


@ActivityScoped
class Repository @Inject constructor(private val cleaningAppApiService: CleaningAppApiService) {
    suspend fun getRooms(): Response<Rooms> {
        return cleaningAppApiService.getRooms()
    }

    suspend fun getRoomsChores(roomId: Int): Response<Chores> {
        Log.i("resp", "what a stupid response")
        return cleaningAppApiService.getRoomsChores(roomId)
    }

    suspend fun getChore(choreId: Int): Response<ChoreItem> {
        return cleaningAppApiService.getChore(choreId)
    }

    suspend fun signInUser(username: String, password: String): Token {
        val signInRequestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("password", password)
            .build()
        return cleaningAppApiService.signInUser(signInRequestBody)
    }

    suspend fun registerUser(username: String, password: String, email: String): User {
        val signUpRequestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("password", password)
            .addFormDataPart("email", email)
            .build()
        return cleaningAppApiService.registerUser(signUpRequestBody)
    }
}