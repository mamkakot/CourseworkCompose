package com.example.courseworkcompose.screens.room

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseworkcompose.data.repository.Repository
import com.example.courseworkcompose.models.room.RoomItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(val rep: Repository) : ViewModel() {
    var roomList = mutableStateOf<List<RoomItem>>(listOf())


    fun getRooms() {
        viewModelScope.launch {
            val result = rep.getRooms()
            Log.d("res:", result.body().toString())
            result.body()?.let { roomList.value = it }
        }
    }
}