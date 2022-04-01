package com.bassem.musicstream.ui.singer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bassem.musicstream.entities.Song
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class SingerViewModel(app: Application) : AndroidViewModel(app) {
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var songsList = MutableLiveData<MutableList<Song>>()

    fun getSingerSongs(singer: String) {
        var songs: MutableList<Song> = mutableListOf()
        db.collection("songs").whereEqualTo("singer", singer).get().addOnCompleteListener {

            if (it.isSuccessful) {
                for (dc in it.result.documentChanges) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        songs.add(dc.document.toObject(Song::class.java))
                    }
                }
                songsList.postValue(songs)
            }
        }
    }
}