package com.example.courseworkcompose.screens.room

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.example.courseworkcompose.R
import com.example.courseworkcompose.TAG
import com.example.courseworkcompose.models.room.RoomItem
import com.example.courseworkcompose.screens.chore.DifficultyDropDown


// TODO: сделать изменение порядка перетаскиванием
@Composable
fun RoomListScreen(navController: NavController, roomViewModel: RoomViewModel = hiltViewModel()) {
    roomViewModel.getRooms()
    val roomList by remember { roomViewModel.roomList }
    val roomNames = roomList.map { it.name }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(roomList) { _, item ->
            RoomView(room = item, navController = navController, roomNames)
        }
    }
}

@Composable
fun RoomView(room: RoomItem, navController: NavController, roomNames: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                Log.d(TAG, room.id.toString())
                navController.navigate("rooms_chores/${room.name}/${room.id}/")
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp
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
                    .size(12.dp),
                    onDraw = {
                        drawCircle(color = Color.Cyan)//Color(room.color.toColorInt()))
                    })
                Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                    Text(text = room.name, style = MaterialTheme.typography.h2)
                    // TODO: переделать на mutableStateOf
                    var countChoresText: String = "${room.count_chores} chores"
//                    var countChoresText: String = when (room.count_chores.toString().last()) {
//                        '1' -> "${room.count_chores} задача"
//                        '2' -> "${room.count_chores} задачи"
//                        '3' -> "${room.count_chores} задачи"
//                        '4' -> "${room.count_chores} задачи"
//                        else -> "${room.count_chores} задач"
//                    }
                    if (room.count_chores == 0) {
                        countChoresText = stringResource(R.string.no_chores)
                    }
                    Text(text = countChoresText, style = MaterialTheme.typography.body1)
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
