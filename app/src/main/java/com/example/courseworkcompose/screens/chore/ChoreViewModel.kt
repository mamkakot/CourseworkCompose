package com.example.courseworkcompose.screens.chore

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseworkcompose.data.repository.Repository
import com.example.courseworkcompose.models.chore.ChoreItem
import kotlinx.coroutines.launch

class ChoreViewModel constructor(roomId: Int) : ViewModel() {
    private val rep = Repository()
    var choreList = mutableStateOf<List<ChoreItem>>(listOf())

    init {
        getChores(roomId)
    }

    private fun getChores(roomId: Int) {
        viewModelScope.launch {
            val result = rep.getRoomsChores(roomId)
            result.body()?.let { choreList.value = it }
        }
    }
}