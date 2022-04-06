package com.bassem.musicstream.ui.play

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.bassem.musicstream.R
import com.bassem.musicstream.databinding.PlayFragmentBinding
import com.bassem.musicstream.entities.Song
import com.bassem.musicstream.entities.StreamPlayer
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import java.lang.Exception

class PlayFragment : Fragment(R.layout.play_fragment), Player.Listener {
    private var _binding: PlayFragmentBinding? = null
    private val binding get() = _binding
    private var song: Song? = null
    private var allSongs: ArrayList<Song>? = null
    private var isPlaying = false
    private var current = 0
    private var issPlaying = MutableLiveData<Boolean>()
    private var share: dataShareInterface? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = this.arguments
        song = args?.get("book") as Song
        allSongs = args.get("list") as ArrayList<Song>
        current = args.getInt("current")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PlayFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity: Activity = context as Activity
        try {
            share = activity as dataShareInterface
        } catch (e: Exception) {
            println(e.message)
        }

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

        val c = StreamPlayer.getMusic().contentDuration.toDouble()
        println("$c current position")

        //Listeners
        binding?.apply {
            playSong.setOnClickListener {
                initPlayer(song!!)


            }
            pauseSong.setOnClickListener {
                StreamPlayer.getMusic().pause()
            }

            nextSong.setOnClickListener {
                StreamPlayer.getMusic().stop()
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


            }

            previousSong.setOnClickListener {
                StreamPlayer.getMusic().stop()
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
            playAuthor.setOnClickListener {
                println("autho")
                val bundle = Bundle()
                bundle.putString("singer", song?.singer)
                val navController =
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                navController.navigate(R.id.action_songFragment_to_singerFragment, bundle)

            }


        }
        StreamPlayer.getMusic().addListener(this)
        binding?.seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, seek: Int, p2: Boolean) {
                println("$seek  SEEK")
                StreamPlayer.getMusic().seekTo(10L)

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })


    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        Log.d("IsPlaying", isPlaying.toString())
        playOrpause(isPlaying)
        updateDuration()
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        println("$playbackState  current postition")
    }


    private fun updateUi(song: Song) {
        binding?.apply {
            playTitle.text = song.name
            playAuthor.text = song.singer
        }
        val cover = binding?.playImage
        val image = song.coverLink
        Glide.with(requireContext()).load(image).into(cover!!)
        share?.getSonginfo(song.name, song.singer, image)


    }

    private fun initPlayer(currentSong: Song) {
        val media: MediaItem = MediaItem.fromUri(currentSong.audioLink)
        StreamPlayer.getMusic().addMediaItem(media)
        StreamPlayer.getMusic().prepare()
        StreamPlayer.getMusic().play()
        issPlaying.postValue(StreamPlayer.getMusic().isPlaying)


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
        while (StreamPlayer.getMusic()!!.isPlaying) {
            handler.postDelayed(Runnable {
                val playbackpostition = StreamPlayer.getMusic()?.currentPosition
                binding?.currentBuffer?.text = playbackpostition.toString()
                handler.postDelayed(Runnable {

                }, 1000)
            }, 1000)
        }
    }

    interface dataShareInterface {
        fun getSonginfo(title: String, singer: String, photo: String)
    }

    private fun stopExo() {
        StreamPlayer.getMusic().stop()
        StreamPlayer.getMusic().seekTo(0L)
        StreamPlayer.getMusic().prepare()
        StreamPlayer.getMusic().play()

    }

    private fun updateDuration() {
        val timeinMill = StreamPlayer.getMusic().duration
        val minutes = timeinMill / 1000 / 60
        val seconds = timeinMill /  1000 % 60
        "${minutes.toInt()}:${seconds.toInt()}".also { binding?.totalBuffer?.text = it }

    }

}