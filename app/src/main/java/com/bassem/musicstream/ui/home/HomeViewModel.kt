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


    fun getBooks() {
        val firestoreList: MutableList<Song> = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db?.collection("books")?.whereEqualTo("category", "book")?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                for (dc in it.result) {
                    val song = dc.toObject(Song::class.java)
                    firestoreList.add(song)
                }

                booksList.postValue(firestoreList)

            }

        }
    }

    fun getNovels() {
        val firestoreList: MutableList<Song> = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db?.collection("books")?.whereEqualTo("category", "novel")?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                for (dc in it.result) {
                    val song = dc.toObject(Song::class.java)
                    firestoreList.add(song)
                }

                novelsList.postValue(firestoreList)

            }

        }
    }

    fun getChildren() {
        val firestoreList: MutableList<Song> = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db?.collection("books")?.whereEqualTo("category", "children")?.get()
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