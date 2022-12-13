package com.example.courseworkcompose.screens.room

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.courseworkcompose.TAG
import com.example.courseworkcompose.models.room.RoomItem


@Composable
fun RoomList(owner: ViewModelStoreOwner) {
    val viewModel = ViewModelProvider(owner)[RoomViewModel::class.java]
    val rList by remember { viewModel.roomList }
    Column() {
        rList.forEach {
            RoomView(room = it)
        }
    }
}

@Composable
fun RoomView(room: RoomItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onRoomClick(room) },
        shape = RoundedCornerShape(10.dp),
        elevation = 12.dp
    ) {
        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Canvas(modifier = Modifier
                    .padding(end = 15.dp)
                    .size(10.dp),
                    onDraw = {
                        drawCircle(color = Color.Cyan)//Color(room.color.toColorInt()))
                    })
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(text = room.name)
                    Text(text = "7 задач")
                }
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 7.dp),
                    progress = room.sum_status / 100f,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

fun onRoomClick(room: RoomItem) {
    Log.d(TAG, room.id.toString())
}
