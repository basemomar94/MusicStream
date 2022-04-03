package com.bassem.musicstream.service

import android.app.*
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.bassem.musicstream.MainActivity
import com.bassem.musicstream.service.Constants.CHANNEL_ID
import com.bassem.musicstream.service.Constants.Music_Notification_Id

class MusicService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        initMusic()
        createNotification()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        return START_STICKY

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        val notificationIntent = Intent(this, MusicService::class.java)
        val pendingIntent = PendingIntent.getActivities(this, 0, arrayOf(notificationIntent), 0)
        val notification: Notification =
            Notification.Builder(this, CHANNEL_ID).setContentTitle("Stream Music")
                .setContentIntent(pendingIntent).build()
        startForeground(Music_Notification_Id, notification)

    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "My Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)


        }
    }

    private fun initMusic() {

    }
}