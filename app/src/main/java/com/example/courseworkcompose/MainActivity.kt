package com.example.courseworkcompose

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.courseworkcompose.data.StoreToken
import com.example.courseworkcompose.screens.calendar.CalendarScreen
import com.example.courseworkcompose.screens.chore.ChoreDetailScreen
import com.example.courseworkcompose.screens.chore.ChoreListScreen
import com.example.courseworkcompose.screens.room.RoomListScreen
import com.example.courseworkcompose.screens.room.RoomViewModel
import com.example.courseworkcompose.screens.user.UserLoginScreen
import com.example.courseworkcompose.screens.user.UserRegistrationScreen
import com.example.courseworkcompose.ui.theme.BottomAppBarColor
import com.example.courseworkcompose.ui.theme.CourseworkComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CourseworkComposeTheme {
                val navController = rememberNavController()
                val roomViewModel = ViewModelProvider(this@MainActivity)[RoomViewModel::class.java]
//                roomViewModel.getRooms()
                val roomList by remember { roomViewModel.roomList }
                val t = StoreToken(LocalContext.current).getToken.collectAsState(initial = "")
                val start = if (t.value != "") "room_list_screen" else "user_register_screen"
                val (fabOnClick, setFabOnClick) = remember { mutableStateOf<(() -> Unit)?>(null) }

                Scaffold(
                    topBar = {
                        AppBarTop(
                            text = "Home",
                            onNavigationIconClick = { Log.i(TAG, "sas") },
                            isGotBack = false
                        )
                    },
                    bottomBar = {
                        AppBarBottom(navController)
                    },

                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                Log.i(TAG, navController.currentDestination.toString())
                                fabOnClick?.invoke()
                            },
                            shape = CircleShape,
                            backgroundColor = BottomAppBarColor,
                            elevation = FloatingActionButtonDefaults.elevation(0.dp),
                        ) {
                            Icon(Icons.Filled.Add, tint = Color.White, contentDescription = "Add")
                        }
                    },
                    isFloatingActionButtonDocked = true,
                    floatingActionButtonPosition = FabPosition.Center,
                    content = { padding ->
                        NavHost(
                            navController = navController,
                            startDestination = start,
                            modifier = Modifier.padding(padding)
                        ) {
                            composable("room_list_screen") {
                                RoomListScreen(navController, setFabOnClick = setFabOnClick)
                            }

                            composable("user_register_screen") {
                                UserRegistrationScreen(navController)
                            }

                            composable("user_login_screen") {
                                UserLoginScreen(navController)
                            }

                            composable("calendar_screen") {
                                CalendarScreen()
                            }

                            composable(
                                "rooms_chores/{roomName}/{roomId}/",
                                arguments = listOf(
                                    navArgument("roomName") {
                                        type = NavType.StringType
                                    },
                                    navArgument("roomId") {
                                        type = NavType.IntType
                                    }
                                )
                            ) {
                                val roomName = remember {
                                    it.arguments?.getString("roomName")
                                }
                                val roomId = remember {
                                    it.arguments?.getInt("roomId")
                                }

                                ChoreListScreen(
                                    roomName = roomName?.lowercase(Locale.ROOT) ?: "",
                                    roomId = roomId!!,
                                    navController = navController,
                                    setFabOnClick = setFabOnClick
                                )
                            }

                            composable("chore_detail_screen/{choreId}/",
                                arguments = listOf(
                                    navArgument("choreId") {
                                        type = NavType.IntType
                                    }
                                ))
                            {
                                val choreId = it.arguments?.getInt("choreId")
                                val roomNames = roomList.map { roomItem -> roomItem.name }
                                ChoreDetailScreen(choreId = choreId!!, roomNames = roomNames)
                            }
                        }
                    }
                )

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CourseworkComposeTheme {

    }
}