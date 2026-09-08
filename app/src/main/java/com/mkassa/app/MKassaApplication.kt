package com.mkassa.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MKassaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize application
    }
}
