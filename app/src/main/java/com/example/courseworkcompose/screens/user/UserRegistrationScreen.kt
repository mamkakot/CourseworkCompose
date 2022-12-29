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
import com.example.courseworkcompose.data.StoreToken
import kotlinx.coroutines.launch

@Composable
fun UserRegistrationScreen(
    navController: NavController,
    userRegistrationViewModel: UserRegistrationViewModel = hiltViewModel()
) {
    val username = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = username.value, onValueChange = { username.value = it })
        TextField(value = email.value, onValueChange = { email.value = it })
        TextField(value = password.value, onValueChange = { password.value = it })

        Button(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 16.dp, end = 16.dp),
            onClick = {
                userRegistrationViewModel.registerUser(
                    username = username.value,
                    password = password.value,
                    email = email.value
                )
            }) {
            Text(
                text = "Register",
                color = Color.White,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "or")
        Spacer(modifier = Modifier.height(12.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(start = 16.dp, end = 16.dp),
            onClick = {
                navController.navigate("user_login_screen")
            }) {
            Text(
                text = "Log in",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}