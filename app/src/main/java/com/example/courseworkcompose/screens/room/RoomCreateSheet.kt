package com.example.courseworkcompose.screens.room

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.courseworkcompose.R
import com.example.courseworkcompose.data.Storage
import com.example.courseworkcompose.models.room.RoomItem
import com.example.courseworkcompose.ui.theme.RoomColors
import com.example.courseworkcompose.ui.theme.ThatWeirdLineColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun ThatWeirdLine() {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(5.dp),
        onDraw = {
            drawRoundRect(
                ThatWeirdLineColor,
                topLeft = center.minus(Offset(x = 75F, y = 0F)),
                size = Size(width = 150F, height = 15F),
                cornerRadius = CornerRadius(15F)
            )
        })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoomCreateSheet(
    viewModel: RoomViewModel = hiltViewModel(),
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {
    // TODO: change this behavior
    val family = Storage(LocalContext.current).getFamilyId.collectAsState(initial = -1)

    val (room, roomSetter) = remember {
        mutableStateOf(
            RoomItem(
                name = "",
                id = -1,
                color = "#${Integer.toHexString(RoomColors[0].toArgb())}",
                count_chores = null,
                sum_status = null,
                family = if (family.value == -1) 1 else family.value!!
            )
        )
    }

    Column(modifier = Modifier.padding(horizontal = 22.dp)) {
        Spacer(modifier = Modifier.height(24.dp))
        ThatWeirdLine()
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Name", style = MaterialTheme.typography.h2)
        Spacer(modifier = Modifier.height(7.dp))
        TextField(
            value = room.name,
            onValueChange = { roomSetter(room.copy(name = it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(text = "Color", style = MaterialTheme.typography.h2)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items(RoomColors) { roomColor ->
                Button(
                    onClick = {
                        println(Integer.toHexString(roomColor.toArgb()))
                        roomSetter(room.copy(color = "#${Integer.toHexString(roomColor.toArgb())}"))
                    },
                    shape = CircleShape,
                    modifier = Modifier.size(32.dp),
                    colors = ButtonDefaults.buttonColors(roomColor)
                ) {

                }
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
            enabled = room.name.isNotBlank(),
            onClick = {
                println(room)
                viewModel.createRoom(room)
                // TODO: fix this crap
                viewModel.getRooms()
                coroutineScope.launch { sheetState.hide() }
            }) {
            Text(
                text = if (room.id != -1) stringResource(R.string.save_button_text)
                else stringResource(R.string.create_button_text)
            )
        }
    }
}