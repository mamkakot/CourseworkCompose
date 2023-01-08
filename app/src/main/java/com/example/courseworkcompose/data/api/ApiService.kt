package com.example.courseworkcompose.data.api

import com.example.courseworkcompose.models.chore.ChoreItem
import com.example.courseworkcompose.models.chore.Chores
import com.example.courseworkcompose.models.invite.InviteItem
import com.example.courseworkcompose.models.invite.Invites
import com.example.courseworkcompose.models.room.Rooms
import com.example.courseworkcompose.models.family.FamilyItem
import com.example.courseworkcompose.models.room.RoomItem
import com.example.courseworkcompose.models.user.Token
import com.example.courseworkcompose.models.user.User
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface CleaningAppApiService {
    @GET("rooms/")
    suspend fun getRooms(): Response<Rooms>

    @POST("rooms/")
    suspend fun createRoom(@Body requestBody: RequestBody): Response<RoomItem>

    @GET("chores/")
    suspend fun getRoomsChores(@Query("room") roomId: Int): Response<Chores>

    @POST("chores/")
    suspend fun postChore(@Body body: RequestBody): Response<ChoreItem>

    @PUT("chores/{choreId}/")
    suspend fun updateChore(
        @Path("choreId") choreId: Int,
        @Body body: RequestBody
    ): Response<ChoreItem>

    @PUT("invites/{inviteId}/")
    suspend fun updateInvite(
        @Path("inviteId") inviteId: Int,
        @Body body: RequestBody
    ): Response<InviteItem>

    @POST("auth/token/login/")
    suspend fun signInUser(@Body body: RequestBody): Response<Token>

    @POST("auth/users/")
    suspend fun registerUser(@Body body: RequestBody): Response<User>

    @GET("chores/{chore}")
    suspend fun getChore(@Path("chore") choreId: Int): Response<ChoreItem>

    @POST("invites/create/")
    suspend fun inviteUserToFamily(@Body body: RequestBody)

    @GET("invites/list/")
    suspend fun getInvites(@Query("receiver") receiverId: Int): Response<Invites>

    @GET("auth/users/me/")
    suspend fun checkUserId(@Header("Authorization") authorization: String): Response<User>

    @GET("families/")
    suspend fun getFamily(): Response<FamilyItem>

    @POST("families/")
    suspend fun postFamily(@Body familyRequestBody: RequestBody): Response<FamilyItem>

    @PUT("families/{familyId}/")
    suspend fun updateFamily(
        @Path("familyId") familyId: Int,
        @Body familyRequestBody: RequestBody
    ): Response<FamilyItem>
}