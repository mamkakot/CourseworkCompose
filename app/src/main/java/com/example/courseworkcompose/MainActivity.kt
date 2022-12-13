package com.example.courseworkcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.courseworkcompose.data.repository.Repository
import com.example.courseworkcompose.models.room.RoomItem
import com.example.courseworkcompose.screens.room.RoomList
import com.example.courseworkcompose.screens.room.RoomView
import com.example.courseworkcompose.ui.theme.CourseworkComposeTheme

const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    var roomsList = emptyList<RoomItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CourseworkComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RoomList(owner = this)

                    roomsList.forEach {
                        RoomView(room = it)
                    }
                }
            }
        }
    }
}

//
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CourseworkComposeTheme {
        RoomView(RoomItem(id = 3, name = "Room3", sum_status = 35, color = "#519c3f"))
    }
}