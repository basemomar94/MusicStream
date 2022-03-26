package com.bassem.musicstream.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bassem.musicstream.entities.Book
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel(app: Application) : AndroidViewModel(app) {
    var db: FirebaseFirestore? = null
    var allsong = MutableLiveData<MutableList<Book>>()


    fun getAllSongs() {
        val firestoreList: MutableList<Book> = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db?.collection("books")?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                for (dc in it.result) {
                    val song = dc.toObject(Book::class.java)
                    firestoreList.add(song)
                    println("${song.name}   SOONG")
                }

                allsong.postValue(firestoreList)

            }

        }
    }
}