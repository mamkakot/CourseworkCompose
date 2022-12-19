package com.example.courseworkcompose.screens.chore

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.courseworkcompose.R
import com.example.courseworkcompose.data.icons.CustomIcons
import com.example.courseworkcompose.screens.room.RoomViewModel
import com.example.courseworkcompose.ui.theme.DropDownColor


@Composable
fun DropDownMenu(
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    Column(horizontalAlignment = Alignment.Start) {
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(horizontal = 22.dp),
            expanded = requestToOpen,
            onDismissRequest = { request(false) },
        ) {
            list.forEach {
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        request(false)
                        selectedString(it)
                    }
                ) {
                    Text(it, modifier = Modifier.wrapContentWidth())
                }
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun DropDownElement(
    itemList: List<String>,
    icon: ImageVector = ImageVector.vectorResource(id = CustomIcons.ArrowDownIcon)
) {
    val text = remember { mutableStateOf(itemList[0]) }
    val isOpen = remember { mutableStateOf(false) }
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        isOpen.value = it
    }
    val userSelectedString: (String) -> Unit = {
        text.value = it
    }

    Box(
        modifier = Modifier
            .height(32.dp)
    ) {
        Column {
            Row {
                BasicTextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    modifier = Modifier
                        .fillMaxSize(),
                    textStyle = MaterialTheme.typography.body2,
                    decorationBox = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(DropDownColor, RoundedCornerShape(5.dp))
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = text.value,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                style = MaterialTheme.typography.body2
                            )
                            Icon(
                                imageVector = icon,
                                contentDescription = null
                            )
                        }
                    })
            }
            DropDownMenu(
                requestToOpen = isOpen.value,
                list = itemList,
                openCloseOfDropDownList,
                userSelectedString
            )
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .clickable(
                    onClick = { isOpen.value = true }
                )
        )
    }
}

@Composable
fun DifficultyDropDown() {
    val difficultyList = listOf(
        "Easy",
        "Normal",
        "Hard",
    )
    DropDownElement(itemList = difficultyList)
}

@Composable
fun FrequencyDropDown() {
    val frequencyList: List<String> = stringArrayResource(id = R.array.frequency_list).toList()
    DropDownElement(itemList = frequencyList)
}

@Composable
fun RoomDropDown(roomNames: List<String>) {
//    roomViewModel.getRooms()
    Log.i("sas23", roomNames.toString())
    DropDownElement(itemList = roomNames)
}

@Composable
fun ChoreDetailScreen(
    choreId: Int,
    roomNames: List<String>,
    viewModel: ChoreDetailsViewModel = hiltViewModel(),
) {
    viewModel.getChore(choreId)
    val chore = remember { viewModel.chore }

    Column(modifier = Modifier.padding(horizontal = 22.dp)) {
        Text(text = "Name", style = MaterialTheme.typography.h2)
        Spacer(modifier = Modifier.height(7.dp))
        DifficultyDropDown()
        Spacer(modifier = Modifier.height(14.dp))
        Text(text = "Room", style = MaterialTheme.typography.h2)
        Spacer(modifier = Modifier.height(7.dp))
        RoomDropDown(roomNames)
        Spacer(modifier = Modifier.height(14.dp))
        Text(text = "Frequency", style = MaterialTheme.typography.h2)
        Spacer(modifier = Modifier.height(7.dp))
        FrequencyDropDown()
    }
}