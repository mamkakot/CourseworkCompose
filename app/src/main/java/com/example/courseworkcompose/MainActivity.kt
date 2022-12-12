package com.example.courseworkcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import com.example.courseworkcompose.data.repository.Repository
import com.example.courseworkcompose.models.room.RoomItem
import com.example.courseworkcompose.models.room.Rooms
import com.example.courseworkcompose.screens.room.RoomViewModel
import com.example.courseworkcompose.ui.theme.CourseworkComposeTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.reflect.KProperty

const val TAG = "MainActivity"
val repository = Repository()

class MainActivity : ComponentActivity() {
    var roomsList = emptyList<RoomItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CourseworkComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RoomList(owner = this)

//                    viewModel.roomList.
//                    val composableScope = rememberCoroutineScope()

//                    ButtonGetRooms(roomList = r)

//                    getRooms(composableScope, r)


                    roomsList.forEach {
                        RoomView(room = it)
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
        RoomView(RoomItem(id = 3, name = "Room3", sum_status = 35, color = "#519c3f"))
    }
}

@Composable
fun RoomList(owner: ViewModelStoreOwner) {
    val viewModel = ViewModelProvider(owner)[RoomViewModel::class.java]
//    val scope = rememberCoroutineScope()
//    var rooms: List<RoomItem> = emptyList<RoomItem>()
    val rList by remember { viewModel.roomList }
    Column() {
        rList.forEach {
            RoomView(room = it)
        }
    }
}

@Composable
fun RoomView(room: RoomItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onRoomClick(room) },
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
                    .size(10.dp),
                    onDraw = {
                        drawCircle(color = Color.Yellow)
                    })
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(text = room.name)
                    Text(text = "7 задач")
                }
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 7.dp),
                    progress = 0.7f,
                    color = Color(R.color.primary)
                )
            }
        }
    }
}

fun onRoomClick(room: RoomItem) {
    Log.d(TAG, room.id.toString())

    runBlocking {
        launch {
            val r = repository.getRoomsChores(room.id)
//            Log.d(TAG, r.body().toString())
        }
    }
}