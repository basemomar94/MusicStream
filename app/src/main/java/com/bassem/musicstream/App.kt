package com.bassem.musicstream

import android.app.Application
import android.util.Log
import com.bassem.musicstream.entities.StreamPlayer

class App :Application() {
    override fun onCreate() {
        super.onCreate()
        StreamPlayer.init(this)
       Log.d("APP","on app class")


    }
}