package com.example.courseworkcompose.models.chore

import androidx.compose.runtime.saveable.mapSaver

data class ChoreItem(
    var date: String,
    var effort: String,
    val id: Int,
    var name: String,
    var period: Int?,
    var period_type: String,
    var room: Int,
    var slave: Int,
    var status: Boolean
)