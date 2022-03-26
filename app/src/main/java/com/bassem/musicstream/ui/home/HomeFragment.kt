package com.bassem.musicstream.ui.home

import android.database.Cursor
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bassem.musicstream.R
import com.bassem.musicstream.adapters.HomeAdapter
import com.bassem.musicstream.databinding.AllsongsFragmentBinding
import com.bassem.musicstream.entities.Song
import com.bassem.musicstream.ui.playsong.SongFragment

class HomeFragment : Fragment(R.layout.allsongs_fragment) {
    private var _binidng: AllsongsFragmentBinding? = null
    private var booksRv: RecyclerView? = null
    private var booksAdapter: HomeAdapter? = null
    private var viewModel: HomeViewModel? = null
    var bottomLayout: LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binidng = AllsongsFragmentBinding.inflate(inflater, container, false)
        return _binidng?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomLayout = activity?.findViewById(R.id.bottomLayout)
        bottomLayout?.visibility = View.VISIBLE
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel?.getAllSongs()

        //Observers
        viewModel?.allsong?.observe(viewLifecycleOwner) {
            if (it != null) {
                RvSetup(it)
                endLoading()
            }

        }


    }

    private fun RvSetup(list: MutableList<Song>) {
        booksRv = _binidng?.recyclerViewHome
        booksAdapter = HomeAdapter(list, requireContext())
        booksRv?.apply {
            adapter = booksAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    private fun endLoading() {
        _binidng?.apply {
            progressLayout.visibility = View.GONE
            recyclerViewHome.visibility = View.VISIBLE
        }
    }


}