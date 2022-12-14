package com.example.courseworkcompose.screens.room

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.example.courseworkcompose.TAG
import com.example.courseworkcompose.models.room.RoomItem
import com.example.courseworkcompose.screens.chore.DifficultyDropDown


@Composable
fun RoomListScreen(viewModel: RoomViewModel, navController: NavController) {
    val rList by remember { viewModel.roomList }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(rList) { _, item ->
            RoomView(room = item, navController = navController)
        }
    }
}

@Composable
fun RoomView(room: RoomItem, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                Log.d(TAG, room.id.toString())
                navController.navigate("rooms_chores/${room.name}/${room.id}/")
            },
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
                    // TODO: переделать на mutableStateOf
                    var countChoresText: String = when (room.count_chores.toString().last()) {
                        '1' -> "${room.count_chores} задача"
                        '2' -> "${room.count_chores} задачи"
                        '3' -> "${room.count_chores} задачи"
                        '4' -> "${room.count_chores} задачи"
                        else -> "${room.count_chores} задач"
                    }
                    if (room.count_chores == 0) {
                        countChoresText = "Нет задач"
                    }
                    Text(text = countChoresText)
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
