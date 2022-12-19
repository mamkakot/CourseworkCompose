package com.example.courseworkcompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CourseworkApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}