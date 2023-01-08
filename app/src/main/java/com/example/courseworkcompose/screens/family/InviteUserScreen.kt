package com.example.courseworkcompose.screens.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.courseworkcompose.data.Storage
import com.example.courseworkcompose.screens.family.FamilyViewModel

@Composable
fun InviteUserScreen(
    navController: NavController,
    userViewModel: FamilyViewModel = hiltViewModel()
) {
    val receiver = remember { mutableStateOf("") }
    val userId = Storage(LocalContext.current).getUserId.collectAsState(initial = -1)
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Enter username to invite")
        TextField(value = receiver.value, onValueChange = { receiver.value = it })
        Button(onClick = {
            if (receiver.value.isNotBlank()) {
                userViewModel.inviteUserToFamily(userId = userId.value!!, receiver = receiver.value)
            }
        }) {
            Text(text = "Invite")
        }
    }
}