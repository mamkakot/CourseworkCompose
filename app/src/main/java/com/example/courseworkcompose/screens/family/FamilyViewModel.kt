package com.example.courseworkcompose.screens.family

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseworkcompose.data.Storage
import com.example.courseworkcompose.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FamilyViewModel @Inject constructor(
    val rep: Repository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val dataStore = Storage(context = context)

    fun createFamily(userId: Int, familyName: String) {
        viewModelScope.launch {
            val family = rep.createFamily(familyName).body()!!
            println(family.id)
            dataStore.saveFamilyName(familyName)
            dataStore.saveFamilyId(family.id)
        }
    }

    fun inviteUserToFamily(userId: Int, receiver: String) {
        viewModelScope.launch {
            rep.inviteUserToFamily(sender = userId, receiver = receiver)
        }
    }

    fun updateFamily(id: Int, name: String) {
        viewModelScope.launch {
            val family = rep.updateFamily(id, name).body()!!

            println(family)
            dataStore.saveFamilyId(family.id)
        }
    }
}