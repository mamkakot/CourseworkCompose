package com.example.courseworkcompose.screens.chore

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.courseworkcompose.TAG
import com.example.courseworkcompose.models.chore.ChoreItem
import com.example.courseworkcompose.screens.room.RoomView
import com.example.courseworkcompose.ui.theme.CourseworkComposeTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChoreListScreen(
    viewModel: ChoreViewModel,
    roomName: String,
    roomId: Int,
    navController: NavController,
) {
    viewModel.getChores(roomId)
    val choreList by remember { viewModel.choreList }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(choreList) { _, item ->
            ChoreCard(chore = item, navController = navController)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChoreCard(chore: ChoreItem, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                navController.navigate("chore_detail_screen/${chore.id}/")
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
                    .size(10.dp), onDraw = {
                    drawCircle(color = Color.Cyan)//Color(room.color.toColorInt()))
                })
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(text = chore.name)
                    Text(text = "7 задач")
                    val zdt: ZonedDateTime = ZonedDateTime.parse(chore.date)
                    val timeLastCleaned: String =
                        zdt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm"))
                    Text(text = "Прошлая уборка: $timeLastCleaned")
                }
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    Checkbox(modifier = Modifier.padding(horizontal = 7.dp),
                        checked = chore.status,
                        onCheckedChange = {
                            Log.i("sss", "changed")
                        })
                }
            }
        }
    }
}

fun onChoreClick(chore: ChoreItem) {
    Log.d(TAG, chore.id.toString())
}
