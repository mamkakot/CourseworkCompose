package com.example.courseworkcompose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.courseworkcompose.models.chore.ChoreItem
import com.example.courseworkcompose.models.room.RoomItem
import com.example.courseworkcompose.screens.chore.ChoreDetailScreen
import com.example.courseworkcompose.screens.chore.ChoreListScreen
import com.example.courseworkcompose.screens.chore.ChoreViewModel
import com.example.courseworkcompose.screens.room.RoomListScreen
import com.example.courseworkcompose.screens.room.RoomView
import com.example.courseworkcompose.screens.room.RoomViewModel
import com.example.courseworkcompose.ui.theme.CourseworkComposeTheme
import java.util.*

const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CourseworkComposeTheme {
                val navController = rememberNavController()
                val choreViewModel =
                    ViewModelProvider(this@MainActivity)[ChoreViewModel::class.java]
                NavHost(
                    navController = navController,
                    startDestination = "room_list_screen",
                ) {
                    composable("room_list_screen") {
                        val roomViewModel =
                            ViewModelProvider(this@MainActivity)[RoomViewModel::class.java]
                        RoomListScreen(roomViewModel, navController)
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
//                        val choreViewModel =
//                            ViewModelProvider(this@MainActivity)[ChoreViewModel::class.java]
                        ChoreListScreen(
                            viewModel = choreViewModel,
                            roomName = roomName?.lowercase(Locale.ROOT) ?: "",
                            roomId = roomId!!,
                            navController = navController
                        )
                    }

                    composable("chore_detail_screen/{choreId}/",
                        arguments = listOf(
                            navArgument("choreId") {
                                type = NavType.IntType
                            }
                        ))
                    {
                        val chore = remember {
                            choreViewModel.choreList.value.find { iter ->
                                iter.id == it.arguments?.getInt("choreId")
                            } ?: ChoreItem("", "", "", 1, "", "", 1, false)
                        }
                        ChoreDetailScreen(
                            choreItem = chore
                        )
                    }
                }
            }
        }
    }
}

//
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CourseworkComposeTheme {

    }
}