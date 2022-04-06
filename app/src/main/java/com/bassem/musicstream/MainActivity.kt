package com.bassem.musicstream

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bassem.musicstream.databinding.ActivityMainBinding
import com.bassem.musicstream.entities.StreamPlayer
import com.bassem.musicstream.ui.play.PlayFragment
import com.bassem.musicstream.ui.play.PlayViewModel
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity(), Player.Listener, PlayFragment.dataShareInterface {
    var binding: ActivityMainBinding? = null
    var exoPlayer: ExoPlayer? = null
    var isPlaying: Callback? = null
    lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer?.addListener(this)
        bottomNavigationView = findViewById(R.id.bottomAppBar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
        /*  Intent(this,MusicService::class.java).also {
              startService(it)
          }*/
        val viewModel = ViewModelProvider(this)[PlayViewModel::class.java]

        binding?.pauseSong2?.setOnClickListener {
            StreamPlayer.getMusic().pause()

        }

        binding?.playSong?.setOnClickListener {
            StreamPlayer.getMusic().play()
        }
        StreamPlayer.getMusic().addListener(this)


    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        playOrpause(StreamPlayer.getMusic().isPlaying)
        println("${StreamPlayer.getMusic().isPlaying} Activity")
    }


    override fun getSonginfo(title: String, singer: String, photo: String) {
        binding?.songTitle?.text = title
        binding?.singer?.text = singer
        val imag = binding?.imgAc
        if (imag != null) {
            Glide.with(this).load(photo).into(imag)
        }
    }

    private fun playOrpause(play: Boolean) {

        if (play) {
            binding?.apply {
                playSong.visibility = View.GONE
                pauseSong2.visibility = View.VISIBLE
            }
        } else {
            binding?.apply {
                playSong.visibility = View.VISIBLE
                pauseSong2.visibility = View.GONE
            }
        }

    }


}