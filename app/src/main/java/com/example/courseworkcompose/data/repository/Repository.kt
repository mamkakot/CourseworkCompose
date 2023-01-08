package com.example.courseworkcompose.data.repository

import com.example.courseworkcompose.data.api.CleaningAppApiService
import com.example.courseworkcompose.models.chore.ChoreItem
import com.example.courseworkcompose.models.chore.Chores
import com.example.courseworkcompose.models.invite.InviteItem
import com.example.courseworkcompose.models.room.Rooms
import com.example.courseworkcompose.models.family.FamilyItem
import com.example.courseworkcompose.models.invite.Invites
import com.example.courseworkcompose.models.room.RoomItem
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
        return cleaningAppApiService.getRoomsChores(roomId)
    }

    suspend fun getChore(choreId: Int): Response<ChoreItem> {
        return cleaningAppApiService.getChore(choreId)
    }

    suspend fun postChore(chore: ChoreItem): Response<ChoreItem> {
        val postChoreRequestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", chore.name)
            .addFormDataPart("date", chore.date)
            .addFormDataPart("period_type", chore.period_type)
            .addFormDataPart("effort", chore.effort)
            .addFormDataPart("status", chore.status.toString())
            .addFormDataPart("room", chore.room.toString())
            .addFormDataPart("slave", chore.slave.toString())
            .build()
        return cleaningAppApiService.postChore(postChoreRequestBody)
    }

    suspend fun createRoom(roomItem: RoomItem): Response<RoomItem> {
        val postRoomRequestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", roomItem.name)
            .addFormDataPart("color", roomItem.color.toString())
            .addFormDataPart("family", roomItem.family.toString())
            .build()
        return cleaningAppApiService.createRoom(postRoomRequestBody)
    }

    suspend fun updateChore(chore: ChoreItem): Response<ChoreItem> {
        val updateChoreRequestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("id", chore.id.toString())
            .addFormDataPart("name", chore.name)
            .addFormDataPart("date", chore.date)
            .addFormDataPart("period_type", chore.period_type)
            .addFormDataPart("effort", chore.effort)
            .addFormDataPart("status", chore.status.toString())
            .addFormDataPart("room", chore.room.toString())
            .addFormDataPart("slave", chore.slave.toString())
            .build()
        return cleaningAppApiService.updateChore(chore.id, updateChoreRequestBody)
    }

    suspend fun updateInvite(invite: InviteItem): Response<InviteItem> {
        val updateInviteRequestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("id", invite.id.toString())
            .addFormDataPart("sender", invite.sender.user.id.toString())
            .addFormDataPart("receiver", invite.receiver.toString())
            .addFormDataPart("is_accepted", invite.is_accepted.toString())
            .build()
        return cleaningAppApiService.updateInvite(invite.id!!, updateInviteRequestBody)
    }

    suspend fun signInUser(username: String, password: String): Response<Token> {
        val signInRequestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("password", password)
            .build()
        return cleaningAppApiService.signInUser(signInRequestBody)
    }

    suspend fun checkUserId(token: String): Response<User> {
        return cleaningAppApiService.checkUserId("Token $token")
    }

    suspend fun registerUser(username: String, password: String, email: String): Response<User> {
        val signUpRequestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("password", password)
            .addFormDataPart("email", email)
            .build()
        return cleaningAppApiService.registerUser(signUpRequestBody)
    }

    suspend fun inviteUserToFamily(sender: Int, receiver: String) {
        val inviteRequestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("sender", sender.toString())
            .addFormDataPart("receiver", receiver)
            .build()
        println("sender: $sender, receiver: $receiver")
        return cleaningAppApiService.inviteUserToFamily(inviteRequestBody)
    }

    suspend fun createFamily(familyName: String): Response<FamilyItem> {
        val familyRequestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", familyName)
            .build()
        return cleaningAppApiService.postFamily(familyRequestBody)
    }

    suspend fun updateFamily(familyId: Int, familyName: String): Response<FamilyItem> {
        val familyRequestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", familyName)
            .build()
        return cleaningAppApiService.updateFamily(familyId, familyRequestBody)
    }

    suspend fun getInvites(receiverId: Int): Response<Invites> {
        return cleaningAppApiService.getInvites(receiverId)
    }
}