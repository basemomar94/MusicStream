package com.bassem.musicstream

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.bassem.musicstream.entities.StreamPlayer
import com.bassem.musicstream.utilities.Constants.NOTIFICATION_CHANNEL_NAME
import com.bassem.musicstream.utilities.Constants.NOTIFICATION_ID

class App : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        StreamPlayer.init(this)

        createNotification()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification() {
        val channel = NotificationChannel(
            NOTIFICATION_ID,
            NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
        )
    }
}