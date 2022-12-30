package com.example.courseworkcompose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.courseworkcompose.data.icons.CustomIcons
import com.example.courseworkcompose.ui.theme.BottomAppBarColor
import com.example.courseworkcompose.ui.theme.InactiveIconColor

@Composable
fun AppBarTop(text: String, onNavigationIconClick: () -> Unit, isGotBack: Boolean) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        modifier = Modifier.height(64.dp),
        elevation = 0.dp,
    ) {
        //TopAppBar Content
        Box(Modifier.fillMaxSize()) {

            if (isGotBack) {
                //Navigation Icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        IconButton(
                            onClick = { onNavigationIconClick() },
                            enabled = true,
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    }
                }
            }

            //Title
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                ProvideTextStyle(value = MaterialTheme.typography.h6) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            text = text,
                            style = MaterialTheme.typography.h1
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun AppBarBottom(navController: NavController) {
    val cutCornerShape = CutCornerShape(50)
    val density = LocalDensity.current
    val shapeSize = density.run { 70.dp.toPx() }
    val outline = cutCornerShape.createOutline(
        Size(shapeSize, shapeSize),
        LocalLayoutDirection.current,
        density
    )
    BottomAppBar(
        backgroundColor = BottomAppBarColor,
        modifier = Modifier.height(45.dp),
        elevation = 0.dp,
        cutoutShape = CircleShape,
        contentPadding = PaddingValues(0.dp)
    ) {
        val items = listOf("room_list_screen", "calendar_screen", "User", "Settings")
        val icons = listOf(
            CustomIcons.HomeIcon,
            CustomIcons.CalendarIcon,
            CustomIcons.UserIcon,
            CustomIcons.SettingsIcon,
        )
        var selectedItem by remember { mutableStateOf(0) }

        BottomNavigation(
            backgroundColor = BottomAppBarColor,
            elevation = 0.dp,
            modifier = Modifier
                .fillMaxSize()
                .align(CenterVertically)
        ) {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = icons[index]),
                            contentDescription = item
                        )
                    },
                    selected = selectedItem == index,
                    selectedContentColor = Color.White,
                    unselectedContentColor = InactiveIconColor,
                    onClick = {
                        selectedItem = index
                        navController.navigate(item)
                    },
                )
                if (index == 1) {
                    Spacer(modifier = Modifier.width(50.dp))
                }
            }
        }
    }
}