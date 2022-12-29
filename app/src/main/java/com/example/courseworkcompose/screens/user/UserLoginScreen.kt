package com.example.courseworkcompose.screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun UserLoginScreen(
    navController: NavController,
    userRegistrationViewModel: UserRegistrationViewModel = hiltViewModel()
) {

    val username = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = username.value, onValueChange = { username.value = it })
        TextField(value = password.value, onValueChange = { password.value = it })

        Button(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 16.dp, end = 16.dp),
            onClick = {
                userRegistrationViewModel.loginUser(
                    username = username.value,
                    password = password.value
                )
            }) {
            Text(
                text = "Log in",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}
