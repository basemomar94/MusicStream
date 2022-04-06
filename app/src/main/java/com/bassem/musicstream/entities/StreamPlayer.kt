package com.bassem.musicstream.entities

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player

class StreamPlayer {
    companion object {
        private var exoPlayer: ExoPlayer? = null
        private var musicplayer: StreamPlayer? = null
        fun init(appContext: Context) {
            exoPlayer = ExoPlayer.Builder(appContext).build()
            musicplayer = StreamPlayer()

        }

        fun getMusic(): ExoPlayer {
            return exoPlayer!!
        }
    }

}