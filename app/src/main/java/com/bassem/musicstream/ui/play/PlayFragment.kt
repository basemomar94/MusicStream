package com.bassem.musicstream.ui.play

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.bassem.musicstream.R
import com.bassem.musicstream.databinding.PlayFragmentBinding
import com.bassem.musicstream.entities.Song
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player

class PlayFragment : Fragment(R.layout.play_fragment), Player.Listener {
    var _binding: PlayFragmentBinding? = null
    val binding get() = _binding
    private var song: Song? = null
    private var allSongs: ArrayList<Song>? = null
    private var isPlaying = false
    private var current = 0
    private var issPlaying = MutableLiveData<Boolean>()


    private var exoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = this.arguments
        song = args?.get("book") as Song
        allSongs = args.get("list") as ArrayList<Song>
        current = args.getInt("current")
        println("$current ////")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PlayFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allSongs?.get(current)?.let {
            updateUi(it)
            initPlayer(it)
        }
        //Observers

        var currentPlayBack = MutableLiveData<Long>()
        currentPlayBack.observe(viewLifecycleOwner) {
            println(it.toString())

        }
        issPlaying.observe(viewLifecycleOwner) {
            playOrpause(it)
            println(it)
        }

        //Listeners
        binding?.apply {
            playSong.setOnClickListener {
                exoPlayer?.play()
                currentPlayBack.postValue(exoPlayer?.bufferedPosition)


            }
            pauseSong.setOnClickListener {
                exoPlayer?.pause()
            }

            nextSong.setOnClickListener {
                exoPlayer?.stop()
                if (current < allSongs!!.size - 1) {
                    current++
                    val song = allSongs?.get(current)
                    updateUi(song!!)
                    initPlayer(song)


                } else {
                    current = 0
                    val song = allSongs?.get(current)
                    updateUi(song!!)
                    initPlayer(song)
                }
                playOrpause(true)

                Log.d("Check", exoPlayer!!.isPlaying.toString())


            }

            previousSong.setOnClickListener {
                exoPlayer?.stop()
                if (current < allSongs!!.size - 1) {
                    current--
                    val song = allSongs?.get(current)
                    updateUi(song!!)
                    initPlayer(song)

                } else {
                    current = 0
                    val song = allSongs?.get(current)
                    updateUi(song!!)
                    initPlayer(song)
                }
                playOrpause(true)


            }


        }
        exoPlayer?.addListener(this)


    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        Log.d("IsPlaying", isPlaying.toString())
        playOrpause(isPlaying)
    }

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        super.onPlayWhenReadyChanged(playWhenReady, reason)
        Log.d("isPlaying", playWhenReady.toString())
    }


    private fun updateUi(song: Song) {
        binding?.apply {
            playTitle.text = song.name
        }
        val cover = binding?.playImage
        val image = song.coverLink
        Glide.with(requireContext()).load(image).into(cover!!)

    }

    private fun initPlayer(currentSong: Song) {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        val media: MediaItem = MediaItem.fromUri(currentSong.audioLink)
        exoPlayer?.addMediaItem(media)
        exoPlayer?.prepare()
        exoPlayer?.play()
        issPlaying.postValue(exoPlayer?.isPlaying)
    }

    private fun playOrpause(play: Boolean) {

        if (play) {
            binding?.apply {
                playSong.visibility = View.GONE
                pauseSong.visibility = View.VISIBLE
            }
        } else {
            binding?.apply {
                playSong.visibility = View.VISIBLE
                pauseSong.visibility = View.GONE
            }
        }

    }

    private fun updatePlayback() {
        val handler = Handler()
        while (exoPlayer!!.isPlaying) {
            handler.postDelayed(Runnable {
                val playbackpostition = exoPlayer?.currentPosition
                binding?.currentBuffer?.text = playbackpostition.toString()
                handler.postDelayed(Runnable {

                }, 1000)
            }, 1000)
        }
    }


}