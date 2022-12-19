package com.example.courseworkcompose.screens.chore

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseworkcompose.data.repository.Repository
import com.example.courseworkcompose.models.chore.ChoreItem
import com.example.courseworkcompose.models.chore.Chores
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChoreDetailsViewModel @Inject constructor(val rep: Repository) : ViewModel() {
    val chore = mutableStateOf<ChoreItem>(ChoreItem("", "", "", 1, "", "", 1, false))

    fun getChore(choreId: Int) {
        lateinit var result : ChoreItem
        viewModelScope.launch {
            chore.value = rep.getChore(choreId).body()!!
        }
    }
}