package com.example.courseworkcompose.screens.chore

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.courseworkcompose.R
import com.example.courseworkcompose.data.icons.CustomIcons
import com.example.courseworkcompose.ui.theme.DropDownColor

@Composable
fun DropDownMenu(
    requestToOpen: Boolean = false,
    list: List<String>,
    width: Dp,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    Column(horizontalAlignment = Alignment.Start) {
        DropdownMenu(
            modifier = Modifier
                .width(width)
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
    val textFieldSize = remember { mutableStateOf(Size.Zero) }

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
                        .onGloballyPositioned { coordinates ->
                            textFieldSize.value = coordinates.size.toSize()
                        }
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
                width = with(LocalDensity.current) { textFieldSize.value.width.toDp() },
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
    Log.i("sas23", roomNames.toString())
    DropDownElement(itemList = roomNames)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TB(modifier: Modifier) {
    val text = remember {
        mutableStateOf("")
    }
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = text.value,
        onValueChange = { if (it.length < 5) text.value = it },
        modifier = modifier
            .height(32.dp)
            .indicatorLine(
                enabled = true, isError = false, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Gray
                ), interactionSource = interactionSource
            ),
        singleLine = true,
        maxLines = 1,
        decorationBox = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent, RoundedCornerShape(5.dp))
                    .padding(horizontal = 23.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text.value,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                    maxLines = 1,
                )
            }
        },
    )
}

@Composable
fun DaysTextField(modifier: Modifier, value: String, onValueChange: (String) -> Unit) {
    val text = remember { mutableStateOf(value) }
    val textFieldSize = remember { mutableStateOf(Size.Zero) }
    BasicTextField(
        value = text.value,
        onValueChange = { text.value = it },
        modifier = Modifier
            .onGloballyPositioned { coordinates ->
                textFieldSize.value = coordinates.size.toSize()
            }
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
            }
        })
}

enum class Priority(val value: Color) {
    PriorityFirst(Color(R.color.priority_first)),
    PrioritySecond(Color(R.color.priority_second)),
    PriorityThird(Color(R.color.priority_third)),
    PriorityForth(Color(R.color.priority_forth)),
    PriorityFirstBack(Color(R.color.priority_first_back)),
    PrioritySecondBack(Color(R.color.priority_second_back)),
    PriorityThirdBack(Color(R.color.priority_third_back)),
    PriorityForthBack(Color(R.color.priority_forth_back)),
}

fun getAllPriorities(): List<Priority> {
    return listOf(
        Priority.PriorityFirst,
        Priority.PrioritySecond,
        Priority.PriorityThird,
        Priority.PriorityForth,
    )
}

fun getPriority(value: Color): Priority? {
    val map = Priority.values().associateBy(Priority::value)
    return map[value]
}

@Preview(showBackground = true)
@Composable
fun Chip(
    color: Color = Color(R.color.priority_first),
    backgroundColor: Color = Color(R.color.priority_first_back),
    isSelected: Boolean = false,
    onSelectionChanged: (Color) -> Unit = {},
) {
    Canvas(modifier = Modifier
        .padding(end = 10.dp)
        .size(16.dp)
        .toggleable(value = isSelected,
            onValueChange = {
                onSelectionChanged(color)
            }),
        onDraw = {
            drawCircle(color = if (isSelected) color else backgroundColor)
        })
}

@Composable
fun PriorityChips(
    priorities: List<Priority> = getAllPriorities(),
    selectedPriority: Priority? = null,
    onSelectedChanged: (Color) -> Unit = {},
) {
    Column(modifier = Modifier.padding(8.dp)) {
        LazyRow {
            items(priorities) {
                Chip(
                    color = it.value,
                    isSelected = selectedPriority == it,
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    },
                )
            }
        }
    }
}

val selectedPriority: MutableState<Priority?> = mutableStateOf(null)

@Composable
fun ChoreDetailScreen(
    choreId: Int,
    roomNames: List<String>,
    viewModel: ChoreDetailsViewModel = hiltViewModel(),
) {
    viewModel.getChore(choreId)
    val chore = remember { viewModel.chore }
    val daysCount = remember { mutableStateOf("2") }

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
        Row(modifier = Modifier.padding(horizontal = 12.dp)) {
            Text(text = "Every", style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.width(23.dp))
            TB(modifier = Modifier.width(88.dp))
            Spacer(modifier = Modifier.width(23.dp))
            FrequencyDropDown()
        }
    }
}