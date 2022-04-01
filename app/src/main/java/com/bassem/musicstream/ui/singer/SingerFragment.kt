package com.bassem.musicstream.ui.singer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bassem.musicstream.R
import com.bassem.musicstream.adapters.SongsListAdapter
import com.bassem.musicstream.databinding.SingerFragmentBinding
import com.bassem.musicstream.entities.Song

class SingerFragment : Fragment(R.layout.singer_fragment), SongsListAdapter.HomeInterface {
    var binding: SingerFragmentBinding? = null
    var singer: String? = null
    var songsRv: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = this.arguments
        singer = args?.getString("singer")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SingerFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this)[SingerViewModel::class.java]
        viewModel.getSingerSongs(singer!!)
        viewModel.songsList.observe(viewLifecycleOwner) {
            initRv(it)
        }
    }

    fun initRv(list: MutableList<Song>) {
        val songsAdapter = SongsListAdapter(list, requireContext(), this)
        songsRv = binding?.singerRv
        songsRv?.apply {
            adapter = songsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    override fun viewBook(song: Song, list: MutableList<Song>, position: Int) {

    }
}