package com.bassem.musicstream.ui.home

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bassem.musicstream.R
import com.bassem.musicstream.adapters.HomeAdapter
import com.bassem.musicstream.databinding.AllsongsFragmentBinding
import com.bassem.musicstream.entities.Song
import java.util.ArrayList

class HomeFragment : Fragment(R.layout.allsongs_fragment), HomeAdapter.HomeInterface {
    private var _binidng: AllsongsFragmentBinding? = null
    private var booksRv: RecyclerView? = null
    private var novelsRv: RecyclerView? = null

    private var booksAdapter: HomeAdapter? = null
    private var novelsAdapter: HomeAdapter? = null

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


        viewModel?.getfolkSongs()
        viewModel?.getpopSongs()
        viewModel?.getrockSongs()

        //Observers
        viewModel?.booksList?.observe(viewLifecycleOwner) {
            if (it != null) {
                initRv(_binidng?.recyclerViewBooks!!, HomeAdapter(it, requireContext(), this))
                endLoading()
            }

        }

        viewModel?.novelsList?.observe(viewLifecycleOwner) {
            if (it != null) {
                initRv(_binidng?.recyclerViewNovels!!, HomeAdapter(it, requireContext(), this))
            }
        }

        viewModel?.childrenList?.observe(viewLifecycleOwner) {
            if (it != null) {
                initRv(_binidng?.recyclerViewChildren!!, HomeAdapter(it, requireContext(), this))

            }
        }


    }

    private fun initRv(rv: RecyclerView, rvAdapter: HomeAdapter) {
        rv.apply {
            adapter = rvAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
    }

    private fun endLoading() {
        _binidng?.apply {
            progressLayout.visibility = View.GONE
            homeLayout.visibility = View.VISIBLE
        }
    }

    override fun viewBook(song: Song, list: MutableList<Song>, position: Int) {
        val bundle = Bundle()
        bundle.putSerializable("book", song)
        bundle.putInt("current", position)
        bundle.putParcelableArrayList("list", list as ArrayList<out Parcelable>)
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.action_homeFragment_to_songFragment, bundle)
    }


}