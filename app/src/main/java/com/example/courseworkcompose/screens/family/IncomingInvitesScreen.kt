package com.example.courseworkcompose.screens.family

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.courseworkcompose.data.Storage

@Composable
fun IncomingInvitesScreen(
    navController: NavController,
    viewModel: InvitesViewModel = hiltViewModel()
) {
    val receiverId = Storage(LocalContext.current).getUserId.collectAsState(initial = -1)
    viewModel.getInvites(receiverId.value!!)

    val inviteList = remember { viewModel.inviteList }
    if (inviteList.value.isEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "There are no invites."
        )
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(inviteList.value) { _, item ->
                InviteCard(username = item.sender.user.username)
            }
        }
    }
}

@Composable
fun InviteCard(username: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    text = "User \"$username\" invites you to join their family"
                )
                IconButton(
                    modifier = Modifier
                        .width(20.dp),
                    onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "accept")
                }
                IconButton(modifier = Modifier
                    .width(20.dp), onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "decline")
                }
            }
        }
    }
}