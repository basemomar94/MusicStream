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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bassem.musicstream.R
import com.bassem.musicstream.databinding.PlayFragmentBinding
import com.bassem.musicstream.entities.Song
import com.bassem.musicstream.entities.StreamPlayer
import com.bassem.musicstream.service.MusicService
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import java.lang.Exception
import kotlin.concurrent.fixedRateTimer

class PlayFragment : Fragment(R.layout.play_fragment), Player.Listener {
    private var _binding: PlayFragmentBinding? = null
    private val binding get() = _binding
    private var song: Song? = null
    private var allSongs: ArrayList<Song>? = null
    private var current = 0
    var viewModel: PlayViewModel? = null
    private var share: DataShareInterface? = null
    private var handler = Handler()


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
            share = activity as DataShareInterface
        } catch (e: Exception) {
            println(e.message)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PlayViewModel::class.java]




        allSongs?.get(current)?.let {
            updateUi(it)
            viewModel!!.initPlayer(it)
        }

        //Observers


        //Listeners
        binding?.apply {
            playSong.setOnClickListener {
                StreamPlayer.getMusic().play()

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


                } else {
                    current = 0
                    val song = allSongs?.get(current)
                    updateUi(song!!)
                }
                playPause(true)


            }

            previousSong.setOnClickListener {
                StreamPlayer.getMusic().stop()
                if (current < allSongs!!.size - 1) {
                    current--
                    val song = allSongs?.get(current)
                    updateUi(song!!)

                } else {
                    current = 0
                    val song = allSongs?.get(current)
                    updateUi(song!!)
                }
                playPause(true)


            }
            playAuthor.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("singer", song?.singer)
                val navController =
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                navController.navigate(R.id.action_songFragment_to_singerFragment, bundle)

            }


        }

        StreamPlayer.getMusic().addListener(this)



    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        println(error.message)
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        val total = viewModel?.updateDuration()
        binding?.totalBuffer?.text = total
        playPause(isPlaying)

        val test = StreamPlayer.getMusic().mediaMetadata.displayTitle
        if (isPlaying) {
            watchProgress()

        }
        Log.d("IsPlaying", test.toString())
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


    private fun playPause(play: Boolean) {

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

    private fun watchProgress() {
        fixedRateTimer("timer", false, 0, 1000) {
            this@PlayFragment.requireActivity().runOnUiThread(Runnable {
                var current = StreamPlayer.getMusic().currentPosition
                 updateProgressUi(current)
            })
        }
    }

    private fun updateProgressUi(current: Long) {
        val minutes = current / 1000 / 60
        val seconds = current / 1000 % 60
        val currentPosition = "${minutes.toInt()}:${seconds.toInt()}"
        binding?.currentBuffer?.text = currentPosition
      //  binding?.seekBar?.max = StreamPlayer.getMusic().duration.toInt()
       // binding?.seekBar?.progress = current.toInt()


    }


    interface DataShareInterface {
        fun getSonginfo(title: String, singer: String, photo: String)
    }


}