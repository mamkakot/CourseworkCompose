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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.courseworkcompose.R
import com.example.courseworkcompose.models.chore.ChoreItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChoreListScreen(
    roomId: Int,
    roomName: String,
    navController: NavController,
    fabOnClick: () -> Unit,
    setCurrentChore: (ChoreItem) -> Unit,
    viewModel: ChoreViewModel = hiltViewModel()
) {
    viewModel.getRoomsChores(roomId)
    val choreList by remember { viewModel.choreList }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(choreList) { _, item ->
            ChoreCard(chore = item, fabOnClick = fabOnClick, setCurrentChore = setCurrentChore)
        }
    }


    LaunchedEffect(Unit) {
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChoreCard(chore: ChoreItem, fabOnClick: () -> Unit, setCurrentChore: (ChoreItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                println(chore)
                setCurrentChore.invoke(chore.copy(name = chore.name))
                fabOnClick()
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
                    Text(text = buildString {
                        append(stringResource(R.string.last_cleanup))
                        append(timeLastCleaned)
                    })
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
