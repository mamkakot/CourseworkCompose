package com.example.courseworkcompose.screens.user

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseworkcompose.data.StoreToken
import com.example.courseworkcompose.data.repository.Repository
import com.example.courseworkcompose.models.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserRegistrationViewModel @Inject constructor(
    val rep: Repository,
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    val user = mutableStateOf<User?>(null)

    val dataStore = StoreToken(context = context)

    fun registerUser(username: String, password: String, email: String) {
        viewModelScope.launch {
            val result = rep.registerUser(username = username, password = password, email = email)
            result.body()?.let { user.value = it }
            if (user.value != null) {
                val token = rep.signInUser(username, password).body()!!
                dataStore.saveToken(token.auth_token)
            }
        }
    }

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            val token = rep.signInUser(username = username, password = password).body()!!
            dataStore.saveToken(token.auth_token)
        }
    }
}