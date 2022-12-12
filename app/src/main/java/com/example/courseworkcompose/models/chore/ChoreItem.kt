package com.example.courseworkcompose.models.chore

data class ChoreItem(
    val condition: String,
    val date: String,
    val effort: String,
    val id: Int,
    val name: String,
    val period: String,
    val room: Int,
    val status: Boolean
)