package com.example.courseworkcompose.data.repository

import com.example.courseworkcompose.data.api.RetrofitInstance
import com.example.courseworkcompose.models.chore.Chores
import com.example.courseworkcompose.models.room.Rooms
import com.example.courseworkcompose.models.user.Token
import com.example.courseworkcompose.models.user.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response


class Repository {
    suspend fun getRooms(): Response<Rooms> {
        return RetrofitInstance.apiService.getRooms()
    }

    suspend fun getRoomsChores(roomId: Int): Chores {
        return RetrofitInstance.apiService.getRoomsChores(roomId)
    }

    suspend fun signInUser(username: String, password: String): Token {
        val signInRequestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("password", password)
            .build()
        return RetrofitInstance.apiService.signInUser(signInRequestBody)
    }

    suspend fun registerUser(username: String, password: String, email: String): User {
        val signUpRequestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("password", password)
            .addFormDataPart("email", email)
            .build()
        return RetrofitInstance.apiService.registerUser(signUpRequestBody)
    }
}