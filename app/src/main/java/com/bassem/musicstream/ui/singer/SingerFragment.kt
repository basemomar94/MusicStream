package com.bassem.musicstream.ui.singer

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bassem.musicstream.R
import com.bassem.musicstream.adapters.SongsListAdapter
import com.bassem.musicstream.databinding.SingerFragmentBinding
import com.bassem.musicstream.entities.Singer
import com.bassem.musicstream.entities.Song
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.Player
import java.util.ArrayList
import kotlin.math.sin

class SingerFragment : Fragment(R.layout.singer_fragment), SongsListAdapter.HomeInterface, Player.Listener {
    var binding: SingerFragmentBinding? = null
    var singer: String? = null
    var songsRv: RecyclerView? = null
    var songsAdapter: SongsListAdapter? = null
    var allsongs: MutableList<Song>? = null

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
        viewModel.getSingerinfo(singer!!)

        //Observers
        viewModel.songsList.observe(viewLifecycleOwner) {
            allsongs = it
            allsongs?.let { it1 -> initRv(it1) }

        }
        viewModel.singerLive.observe(viewLifecycleOwner) {
            updateSingerInfo(it)
            endLoading()
        }

        //Listeners
        binding?.search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                println(p0)
                if (p0 != null) {
                    search(p0)
                }
                return true
            }
        })

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
        val bundle = Bundle()
        bundle.putSerializable("book", song)
        bundle.putInt("current", position)
        bundle.putParcelableArrayList("list", list as ArrayList<out Parcelable>)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.action_singerFragment_to_songFragment, bundle)

    }

    private fun updateSingerInfo(singer: Singer) {
        println(singer.name)
        binding?.singerName?.text = singer.name
        val imageLink = singer.photo
        Glide.with(requireView()).load(imageLink).into(binding?.singerPhoto!!)
    }

    private fun endLoading() {
        binding?.apply {
            shimmerLayout.visibility = View.GONE
            singerLayout.visibility = View.VISIBLE

        }
    }



  private  fun search(input: String) {
        val searchList: MutableList<Song> = mutableListOf()
      allsongs?.forEach {
            if (it.name.contains(input))
                searchList.add(it)
        }
      println(searchList)
       songsAdapter?.addList(searchList)
      songsAdapter?.notifyDataSetChanged()
    }


}