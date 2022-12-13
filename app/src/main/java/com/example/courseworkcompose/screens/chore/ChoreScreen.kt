package com.example.courseworkcompose.screens.chore

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.courseworkcompose.TAG
import com.example.courseworkcompose.models.chore.ChoreItem
import com.example.courseworkcompose.ui.theme.CourseworkComposeTheme

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CourseworkComposeTheme {
        ChoreCard(
            ChoreItem(
                id = 3,
                name = "Chore1",
                condition = "Bad",
                effort = "Hard",
                room = 1,
                period = "Once a week",
                status = true,
                date = "12/22/2022"
            )
        )
    }
}

@Composable
fun ChoreList(owner: ViewModelStoreOwner) {
    val viewModel = ViewModelProvider(owner)[ChoreViewModel::class.java]
    val rList by remember { viewModel.choreList }
    Column() {
        rList.forEach {
            ChoreCard(chore = it)
        }
    }
}

@Composable
fun ChoreCard(chore: ChoreItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onChoreClick(chore) },
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
                }
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                    Checkbox(modifier = Modifier.padding(horizontal = 7.dp),
                        checked = chore.status,
                        onCheckedChange = {
                            Log.i("sss", "changed")
                        })
                }

//                LinearProgressIndicator(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 7.dp),
//                    progress = room.sum_status / 100f,
//                    color = MaterialTheme.colors.primary
//                )
            }
        }
    }
}

fun onChoreClick(chore: ChoreItem) {
    Log.d(TAG, chore.id.toString())
}
