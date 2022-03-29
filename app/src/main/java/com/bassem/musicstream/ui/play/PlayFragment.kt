package com.bassem.musicstream.ui.play

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.bassem.musicstream.R
import com.bassem.musicstream.databinding.PlayFragmentBinding
import com.bassem.musicstream.entities.Book
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class PlayFragment : Fragment(R.layout.play_fragment) {
    var _binding: PlayFragmentBinding? = null
    val binding get() = _binding
    private var book: Book? = null
    private var isPlaying = false


    private var exoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = this.arguments
        book = args?.get("book") as Book
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
        book?.let {
            updateUi(it)
            initPlayer()
        }
        //Observers

        var currentPlayBack = MutableLiveData<Long>()
        currentPlayBack.observe(viewLifecycleOwner) {
            println(it.toString())

        }

        //Listeners
        binding?.apply {
            playSong.setOnClickListener {
                exoPlayer?.play()
                playOrpause(true)
                //  updatePlayback()
                currentPlayBack.postValue(exoPlayer?.bufferedPosition)


            }
            pauseSong.setOnClickListener {
                exoPlayer?.pause()
                playOrpause(false)
            }


        }



    }


    private fun updateUi(book: Book) {
        binding?.apply {
            playTitle.text = book.name
        }
        val cover = binding?.playImage
        val image = book.coverLink
        Glide.with(requireContext()).load(image).into(cover!!)

    }

    private fun initPlayer() {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        val media: MediaItem = MediaItem.fromUri(book!!.audioLink)
        exoPlayer?.addMediaItem(media)
        exoPlayer?.prepare()
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