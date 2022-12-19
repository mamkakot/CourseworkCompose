package com.example.courseworkcompose.screens.room

import android.util.Log
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
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(val rep: Repository) : ViewModel() {
    var roomList = mutableStateOf<List<RoomItem>>(listOf())
//    var roomNames= mutableStateOf<List<String>>(listOf())

    fun getRooms() {
        viewModelScope.launch {
            val result = rep.getRooms()
            Log.d("res:", result.body().toString())
            result.body()?.let { roomList.value = it }
        }
    }

//    fun getRoomNames() {
//        viewModelScope.launch {
//            val result = rep.getRooms()
//            Log.d("res:", result.body().toString())
//            result.body()?.let { rooms -> roomNames.value = rooms.map { it.name } }
//        }
//    }
}