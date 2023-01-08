package com.example.courseworkcompose.screens.family

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseworkcompose.data.repository.Repository
import com.example.courseworkcompose.models.invite.InviteItem
import com.example.courseworkcompose.models.room.RoomItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvitesViewModel @Inject constructor(
    val rep: Repository
) : ViewModel() {
    val inviteList = mutableStateOf<List<InviteItem>>(listOf())

    fun getInvites(receiverId: Int) {
        viewModelScope.launch {
            val invites = rep.getInvites(receiverId)
            invites.body()?.let { inviteList.value = it }
        }
    }
}