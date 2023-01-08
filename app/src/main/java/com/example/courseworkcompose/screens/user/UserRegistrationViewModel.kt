package com.example.courseworkcompose.screens.user

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.courseworkcompose.data.Storage
import com.example.courseworkcompose.data.repository.Repository
import com.example.courseworkcompose.models.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserRegistrationViewModel @Inject constructor(
    val rep: Repository,
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    val user = mutableStateOf<User?>(null)

    val dataStore = Storage(context = context)

    fun registerUser(username: String, password: String, email: String) {
        viewModelScope.launch {
            val result = rep.registerUser(username = username, password = password, email = email)
            result.body()?.let { user.value = it }
            if (user.value != null) {
                dataStore.saveUserId(user.value!!.id)
                loginUser(username, password)
            }
        }
    }

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            val token = rep.signInUser(username = username, password = password).body()!!
            dataStore.saveToken(token.auth_token)
//            dataStore.saveUserId(2)
            // TODO: реализовать запрос нормально
            val result = rep.checkUserId(token.auth_token)
            result.body()?.let { user.value = it }
            println("user: " + user.value)
            if (user.value != null) {
                dataStore.saveUserId(user.value!!.id)
            }
        }
    }
}