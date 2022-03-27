package com.bassem.musicstream.ui.play

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.google.android.exoplayer2.ExoPlayer
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


    fun initExpoPlayer() {
        exoPlayer = ExoPlayer.Builder(context).build()
        exoPlayer?.playWhenReady = true

    }
}