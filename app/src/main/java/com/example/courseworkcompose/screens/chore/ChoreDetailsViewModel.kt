package com.example.courseworkcompose.screens.chore

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseworkcompose.data.repository.Repository
import com.example.courseworkcompose.models.chore.ChoreItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChoreDetailsViewModel @Inject constructor(val rep: Repository) : ViewModel() {
    val chore: MutableState<ChoreItem?> = mutableStateOf(null)

    fun getChore(choreId: Int) {
        viewModelScope.launch {
            chore.value = rep.getChore(choreId).body()!!
        }
    }

    fun updateChore(chore: ChoreItem) {
        viewModelScope.launch {
            rep.updateChore(chore)
        }
    }
}