package com.example.courseworkcompose.screens.calendar

import android.util.Log
import androidx.compose.runtime.Composable
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.model.KalendarType

@Composable
fun CalendarScreen() {
    Kalendar(
        kalendarType = KalendarType.Firey,
        onCurrentDayClick = { kDay, l -> run { Log.i(l.toString(), kDay.toString()) } })
}