package com.bassem.musicstream.ui.home

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bassem.musicstream.R
import com.bassem.musicstream.databinding.AllsongsFragmentBinding
import com.bassem.musicstream.entities.Song
import com.bassem.musicstream.ui.playsong.SongFragment

class HomeFragment : Fragment(R.layout.allsongs_fragment) {
    private var _binidng: AllsongsFragmentBinding? = null
    private val binding get() = _binidng
    private val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    private val projection = arrayOf<String>(
        MediaStore.Audio.AudioColumns.DATA,
        MediaStore.Audio.AudioColumns.TITLE,
        MediaStore.Audio.ArtistColumns.ARTIST,
    )
    private val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
    private val c: Cursor? = context?.contentResolver?.query(
        uri, projection, selection, null, null
    )



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


    }




}