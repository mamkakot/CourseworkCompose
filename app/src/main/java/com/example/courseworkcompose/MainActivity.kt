package com.example.courseworkcompose

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.courseworkcompose.data.Storage
import com.example.courseworkcompose.models.chore.ChoreItem
import com.example.courseworkcompose.screens.calendar.CalendarScreen
import com.example.courseworkcompose.screens.chore.ChoreDetailScreen
import com.example.courseworkcompose.screens.chore.ChoreListScreen
import com.example.courseworkcompose.screens.family.FamilyActionScreen
import com.example.courseworkcompose.screens.family.FamilyCreateScreen
import com.example.courseworkcompose.screens.family.IncomingInvitesScreen
import com.example.courseworkcompose.screens.room.RoomCreateSheet
import com.example.courseworkcompose.screens.room.RoomListScreen
import com.example.courseworkcompose.screens.room.RoomViewModel
import com.example.courseworkcompose.screens.user.InviteUserScreen
import com.example.courseworkcompose.screens.user.UserLoginScreen
import com.example.courseworkcompose.screens.user.UserRegistrationScreen
import com.example.courseworkcompose.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CourseworkComposeTheme {
                val navController = rememberNavController()
                val t = Storage(LocalContext.current).getToken.collectAsState(initial = "")
                val start =
                    if (t.value!!.isNotBlank()) "room_list_screen" else "user_register_screen"

                val coroutineScope = rememberCoroutineScope()
                val modalBottomSheetState =
                    rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
                val (fabOnClick, setFabOnClick) = remember {
                    mutableStateOf<(() -> Unit)?>(
                        null
                    )
                }
                val (currentChore, setCurrentChore) = remember {
                    mutableStateOf<ChoreItem?>(
                        ChoreItem(
                            date = "",
                            effort = "",
                            id = -1,
                            name = "",
                            period = null,
                            period_type = "",
                            slave = -1,
                            status = false,
                            room = -1
                        )
                    )
                }

                BackHandler(modalBottomSheetState.isVisible) {
                    closeBottomSheet(modalBottomSheetState, coroutineScope)
                }

                ModalBottomSheetLayout(
                    sheetContent = {
                        Box(Modifier.fillMaxSize()) {
                            when (navController.currentDestination?.route) {
                                "room_list_screen" -> RoomCreateSheet(
                                    sheetState = modalBottomSheetState,
                                    coroutineScope = coroutineScope
                                )
                                "rooms_chores/{roomName}/{roomId}/" -> {
                                    val roomViewModel: RoomViewModel = hiltViewModel()
                                    val roomList = remember { roomViewModel.roomList }
                                    val roomNames = roomList.map { roomItem -> roomItem.name }
                                    val chore = remember { mutableStateOf(currentChore) }

//                                    println(chore)
                                    ChoreDetailScreen(
                                        choreItem = currentChore!!,
                                        roomNames = roomNames,
                                        setChore = setCurrentChore,
                                        coroutineScope = coroutineScope,
                                        sheetState = modalBottomSheetState
                                    )
                                }
                            }
                        }
                    },

                    sheetState = modalBottomSheetState,
                    sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    sheetBackgroundColor = Color.White, //colorResource(id = R.color.priority_first_back),
                    scrimColor = BottomSheetBackgroundColor,
                ) {
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
                                Icon(
                                    Icons.Filled.Add,
                                    tint = Color.White,
                                    contentDescription = "Add"
                                )
                            }
                        },
                        isFloatingActionButtonDocked = true,
                        floatingActionButtonPosition = FabPosition.Center,
                        content = { padding ->
                            NavHost(
                                navController = navController,
                                startDestination = start,
                                modifier = Modifier
                                    .padding(padding),
                            ) {
                                setFabOnClick {
                                    coroutineScope.launch {
                                        modalBottomSheetState.show()
                                        println(currentChore)
                                    }
                                }
                                composable("room_list_screen") {
                                    RoomListScreen(navController)
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

                                composable("family_screen") {
                                    FamilyCreateScreen(navController = navController)
                                }

                                composable("invite_screen") {
                                    InviteUserScreen(navController = navController)
                                }

                                composable("invite_list_screen") {
                                    IncomingInvitesScreen(navController = navController)
                                }

                                composable("family_action_screen") {
                                    FamilyActionScreen(navController = navController)
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
                                        fabOnClick = fabOnClick!!,
                                        setCurrentChore = setCurrentChore
                                    )
                                }

//                                composable("chore_detail_screen/{choreId}/",
//                                    arguments = listOf(
//                                        navArgument("choreId") {
//                                            type = NavType.IntType
//                                        }
//                                    ))
//                                {
//                                    val choreId = it.arguments?.getInt("choreId")
//                                    val choreViewModel: ChoreDetailsViewModel = hiltViewModel()
//                                    val roomViewModel: RoomViewModel = hiltViewModel()
//                                    val roomList = remember { roomViewModel.roomList }
//                                    choreViewModel.getChore(choreId!!)
//                                    val roomNames = roomList.map { roomItem -> roomItem.name }
//                                    ChoreDetailScreen(
//                                        choreItem = choreViewModel.chore,
//                                        roomNames = roomNames,
//                                        navController = navController
//                                    )
//                                }
                            }
                        }
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    private fun closeBottomSheet(
        modalBottomSheetState: ModalBottomSheetState,
        coroutineScope: CoroutineScope
    ) {
        coroutineScope.launch { modalBottomSheetState.hide() }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CourseworkComposeTheme {

    }
}