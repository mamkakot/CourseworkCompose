package com.example.courseworkcompose.models.invite

import com.example.courseworkcompose.models.user.User
import com.google.gson.JsonElement

data class InviteItem(
    val id: Int?,
    val sender: Sender,
    val receiver: Int?,
    val is_accepted: Boolean,
)

data class Sender(
    val user: User
)
