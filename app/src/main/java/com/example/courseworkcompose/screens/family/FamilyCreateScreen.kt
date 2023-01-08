package com.example.courseworkcompose.screens.family

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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

@Composable
fun FamilyCreateScreen(navController: NavController, viewModel: FamilyViewModel = hiltViewModel()) {
    val savedFamilyName = Storage(LocalContext.current).getFamilyName.collectAsState(initial = "")
    val savedFamilyId = Storage(LocalContext.current).getFamilyId.collectAsState(initial = -1)

    val familyName = remember { mutableStateOf(savedFamilyName.value) }
    val userId = Storage(LocalContext.current).getUserId.collectAsState(initial = -1)

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "How should we call your family?")
        TextField(value = familyName.value!!, onValueChange = { familyName.value = it })
        Button(onClick = {
            if (savedFamilyId.value != -1)
                viewModel.createFamily(userId.value!!, familyName.value!!)
            else
                viewModel.updateFamily(savedFamilyId.value!!, familyName.value!!)
            navController.navigate("invite_screen")
        }, enabled = familyName.value!!.isNotBlank()) {
            Text(text = if (savedFamilyId.value != -1) "Submit" else "Save")
        }
    }
}