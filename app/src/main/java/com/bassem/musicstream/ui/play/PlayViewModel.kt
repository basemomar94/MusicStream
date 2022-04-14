package com.bassem.musicstream.ui.play

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bassem.musicstream.entities.Song
import com.bassem.musicstream.entities.StreamPlayer
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import java.security.CodeSource

class PlayViewModel(app: Application) : AndroidViewModel(app) {
    private var exoPlayer: ExoPlayer? = null
    private val context = app.applicationContext


    fun initPlayer(currentSong: Song) {
        val media: MediaItem = MediaItem.fromUri(currentSong.audioLink)
        StreamPlayer.getMusic().addMediaItem(media)
        StreamPlayer.getMusic().prepare()
        StreamPlayer.getMusic().play()
    }

    fun updateDuration(): String {
        val timeinMill = StreamPlayer.getMusic().duration
        val minutes = timeinMill / 1000 / 60
        val seconds = timeinMill / 1000 % 60
        return if (seconds < 10) {
            "${minutes.toInt()}:0${seconds.toInt()}"
        } else {
            "${minutes.toInt()}:${seconds.toInt()}"

        }
    }
}