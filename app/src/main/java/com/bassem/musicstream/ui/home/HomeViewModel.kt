package com.bassem.musicstream.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bassem.musicstream.entities.Song
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel(app: Application) : AndroidViewModel(app) {
    var db: FirebaseFirestore? = null
    var booksList = MutableLiveData<MutableList<Song>>()
    var novelsList = MutableLiveData<MutableList<Song>>()
    var childrenList = MutableLiveData<MutableList<Song>>()


    fun getfolkSongs() {
        val firestoreList: MutableList<Song> = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db?.collection("songs")?.whereEqualTo("category", "folk")?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                for (dc in it.result) {
                    val song = dc.toObject(Song::class.java)
                    firestoreList.add(song)
                }

                booksList.postValue(firestoreList)

            }

        }
    }

    fun getpopSongs() {
        val firestoreList: MutableList<Song> = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db?.collection("songs")?.whereEqualTo("category", "pop")?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                for (dc in it.result) {
                    val song = dc.toObject(Song::class.java)
                    firestoreList.add(song)
                }

                novelsList.postValue(firestoreList)

            }

        }
    }

    fun getrockSongs() {
        val firestoreList: MutableList<Song> = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db?.collection("songs")?.whereEqualTo("category", "rock")?.get()
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    for (dc in it.result) {
                        val song = dc.toObject(Song::class.java)
                        firestoreList.add(song)
                    }

                    childrenList.postValue(firestoreList)

                }

            }
    }
}