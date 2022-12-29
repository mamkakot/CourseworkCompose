package com.example.courseworkcompose.models.chore

data class ChoreItem(
    val date: String,
    val effort: String,
    val id: Int,
    val name: String,
    val period: Int,
    val period_type: String,
    val room: Int,
    val slave: Int,
    val status: Boolean
)