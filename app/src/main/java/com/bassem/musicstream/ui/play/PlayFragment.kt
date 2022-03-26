package com.bassem.musicstream.ui.play

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bassem.musicstream.R
import com.bassem.musicstream.databinding.PlayFragmentBinding
import com.bassem.musicstream.entities.Book
import com.bumptech.glide.Glide

class PlayFragment : Fragment(R.layout.play_fragment) {
    var _binding: PlayFragmentBinding? = null
    val binding get() = _binding
    private var book: Book? = null

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
        book?.let { updateUi(it) }
    }


    private fun updateUi(book: Book) {
        binding?.apply {
            playTitle.text = book.name
        }
        val cover = binding?.playImage
        val image = book.coverLink
        Glide.with(requireContext()).load(image).into(cover!!)

    }
}