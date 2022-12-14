package com.example.courseworkcompose.screens.chore

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseworkcompose.data.repository.Repository
import com.example.courseworkcompose.models.chore.ChoreItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChoreViewModel @Inject constructor(val rep: Repository) : ViewModel() {
    val choreList = mutableStateOf<List<ChoreItem>>(listOf())

    fun getRoomsChores(roomId: Int) {
        viewModelScope.launch {
            val result = rep.getRoomsChores(roomId)
            result.body()?.let { choreList.value = it }
        }
    }
}