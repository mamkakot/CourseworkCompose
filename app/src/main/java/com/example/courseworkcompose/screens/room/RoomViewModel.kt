package com.example.courseworkcompose.screens.room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseworkcompose.data.repository.Repository
import com.example.courseworkcompose.models.room.RoomItem
import com.example.courseworkcompose.models.room.Rooms
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import retrofit2.Response

class RoomViewModel : ViewModel() {
    val rep = Repository()
    var roomList = mutableStateOf<List<RoomItem>>(listOf())

    init {
        getRooms()
    }

    fun getRooms() {
        viewModelScope.launch {
            val result = rep.getRooms()
            result.body()?.let { roomList.value = it }
        }
    }
}