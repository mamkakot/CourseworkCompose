package com.example.courseworkcompose.screens.family

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun FamilyActionScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate("invite_screen") }) {
            Text(text = "Invite users to your family")
        }
        Button(onClick = { navController.navigate("invite_list_screen") }) {
            Text(text = "See incoming invites")
        }
        Button(onClick = { navController.navigate("family_screen") }) {
            Text(text = "Create family")
        }
    }
}