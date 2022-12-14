package com.example.courseworkcompose.screens.chore

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.courseworkcompose.R
import com.example.courseworkcompose.models.chore.ChoreItem


@Composable
fun DropDownMenu(
    requestToOpen: Boolean = false,
    list: List<String>,
    request: (Boolean) -> Unit,
    selectedString: (String) -> Unit
) {
    Column(horizontalAlignment = Alignment.Start) {
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
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
    icon: @Composable (() -> Unit) = { Icon(Icons.Rounded.ArrowDropDown, null) }
) {
    val text = remember { mutableStateOf(itemList[0]) }
    val isOpen = remember { mutableStateOf(false) }
    val openCloseOfDropDownList: (Boolean) -> Unit = {
        isOpen.value = it
    }
    val userSelectedString: (String) -> Unit = {
        text.value = it
    }
    Box {
        Column {
            Row {
                OutlinedTextField(
                    trailingIcon = icon,
                    value = text.value,
                    onValueChange = { text.value = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(),
                )
//                Icon(
//                    imageVector = Icons.Rounded.List,
//                    contentDescription = "ses"
//                )
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
//                .padding(10.dp)
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
    val frequencyList = listOf(
        "Once a day",
        "Once a week",
        "Once a month",
        "Once a year",
        "Once",
    )
    DropDownElement(itemList = frequencyList)
}

@Composable
fun ChoreDetailScreen(choreItem: ChoreItem) {
    Column {
        Text(text = "Difficulty")
        DifficultyDropDown()
        Text(text = "Frequency")
        FrequencyDropDown()
    }
}