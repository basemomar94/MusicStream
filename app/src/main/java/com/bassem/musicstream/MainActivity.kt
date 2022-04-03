package com.bassem.musicstream

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bassem.musicstream.databinding.ActivityMainBinding
import com.bassem.musicstream.service.MusicService
import com.bassem.musicstream.ui.play.PlayFragment
import com.bassem.musicstream.ui.play.PlayViewModel
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import pub.devrel.easypermissions.EasyPermissions
import java.util.jar.Manifest
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity(), Player.Listener, PlayFragment.dataShareInterface {
    var binding: ActivityMainBinding? = null
    var exoPlayer: ExoPlayer? = null
    var isPlaying: Callback? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer?.addListener(this)

        /*  Intent(this,MusicService::class.java).also {
              startService(it)
          }*/
        val viewModel = ViewModelProvider(this)[PlayViewModel::class.java]


    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
        super.onPlaybackParametersChanged(playbackParameters)
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        println("$isPlaying Activitiy")
    }

    override fun getSonginfo(title: String, singer: String, photo: String) {
        binding?.songTitle?.text = title
        binding?.singer?.text = singer
        val imag = binding?.imgAc
        if (imag != null) {
            Glide.with(this).load(photo).into(imag)
        }
    }


}