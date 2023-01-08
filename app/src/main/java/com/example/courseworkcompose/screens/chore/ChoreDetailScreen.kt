package com.example.courseworkcompose.screens.chore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.courseworkcompose.R
import com.example.courseworkcompose.data.icons.CustomIcons
import com.example.courseworkcompose.models.chore.ChoreItem
import com.example.courseworkcompose.screens.room.ThatWeirdLine
import com.example.courseworkcompose.ui.theme.DarkVeryPeri
import com.example.courseworkcompose.ui.theme.DropDownColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
    selectedIndex: Int,
    icon: ImageVector = ImageVector.vectorResource(id = CustomIcons.ArrowDownIcon)
) {
    val text =
        remember { mutableStateOf(itemList[if (selectedIndex != -1) selectedIndex else 0]) }
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
fun FrequencyDropDown(selectedItem: String) {
    val frequencyList: List<String> = stringArrayResource(id = R.array.frequency_list).toList()
    val selectedIndex = frequencyList.indexOf(selectedItem)
    DropDownElement(itemList = frequencyList, selectedIndex = selectedIndex)
}

@Composable
fun RoomDropDown(roomNames: List<String>, selectedIndex: Int) {
    DropDownElement(itemList = roomNames, selectedIndex = selectedIndex)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TB(modifier: Modifier, text: String, maxLength: Int, onValueChange: (String) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val innerText = remember { mutableStateOf(text) }
    BasicTextField(
        value = innerText.value,
        onValueChange = {
            if (it.length < maxLength) {
                innerText.value = it
                onValueChange.invoke(innerText.value)
            }
        },
        modifier = modifier
            .height(32.dp)
            .indicatorLine(
                enabled = true, isError = false, colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = DarkVeryPeri,
                    unfocusedIndicatorColor = DarkVeryPeri
                ), interactionSource = interactionSource
            ),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
        singleLine = true,
        maxLines = 1,
        decorationBox = { innerTextField ->
            if (innerText.value.isEmpty())
                Text(
                    text = "Input name",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body1,
                )
            innerTextField()
        },
        cursorBrush = SolidColor(DarkVeryPeri)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChoreDetailScreen(
    choreItem: ChoreItem,
    roomNames: List<String>,
    viewModel: ChoreDetailsViewModel = hiltViewModel(),
    setChore: (ChoreItem?) -> Unit,
    sheetState: ModalBottomSheetState,
    coroutineScope: CoroutineScope
) {
//    val (chore, choreSetter) = remember { choreItem }

    if (choreItem != null)
        Column(modifier = Modifier.padding(horizontal = 22.dp)) {
            Spacer(modifier = Modifier.height(24.dp))
            ThatWeirdLine()
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Name", style = MaterialTheme.typography.h2)
            Spacer(modifier = Modifier.height(7.dp))
            TB(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                text = choreItem.name,
                onValueChange = {
//                    choreSetter(chore.copy(name = it))
                    setChore.invoke(choreItem.copy(name = it))
                },
                maxLength = 60
            )
//            TextField(
//                value = chore.name,
//                onValueChange = { choreSetter(chore.copy(name = it)) },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 12.dp)
//            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "Room", style = MaterialTheme.typography.h2)
            Spacer(modifier = Modifier.height(7.dp))
            RoomDropDown(roomNames, selectedIndex = choreItem.room)
            Spacer(modifier = Modifier.height(14.dp))
            Text(text = "Frequency", style = MaterialTheme.typography.h2)
            Spacer(modifier = Modifier.height(7.dp))
            Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                Text(text = "Every", style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.width(23.dp))
                TB(
                    text = if (choreItem.period != null) choreItem.period.toString() else "",
                    onValueChange = {
//                        choreSetter(chore.copy(period = if (it.toIntOrNull() == null) null else it.toInt()))
                        setChore.invoke(choreItem.copy(period = if (it.toIntOrNull() == null) null else it.toInt()))
                    },
                    modifier = Modifier
                        .width(88.dp)
                        .padding(horizontal = 12.dp),
                    maxLength = 5
                )
                Spacer(modifier = Modifier.width(23.dp))
                FrequencyDropDown(selectedItem = choreItem.period_type)
            }

            Spacer(modifier = Modifier.height(7.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
                onClick = {
                    println(choreItem)

                    coroutineScope.launch { sheetState.hide() }
//                    viewModel.updateChore(chore)
                }) {
                Text(
                    text = if (choreItem.id != -1) stringResource(R.string.save_button_text)
                    else stringResource(R.string.create_button_text)
                )
            }
        }
}