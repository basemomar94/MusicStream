package com.bassem.musicstream.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bassem.musicstream.entities.Book
import com.google.firebase.firestore.FirebaseFirestore

class HomeViewModel(app: Application) : AndroidViewModel(app) {
    var db: FirebaseFirestore? = null
    var booksList = MutableLiveData<MutableList<Book>>()
    var novelsList = MutableLiveData<MutableList<Book>>()
    var childrenList = MutableLiveData<MutableList<Book>>()


    fun getBooks() {
        val firestoreList: MutableList<Book> = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db?.collection("books")?.whereEqualTo("category", "book")?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                for (dc in it.result) {
                    val song = dc.toObject(Book::class.java)
                    firestoreList.add(song)
                }

                booksList.postValue(firestoreList)

            }

        }
    }

    fun getNovels() {
        val firestoreList: MutableList<Book> = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db?.collection("books")?.whereEqualTo("category", "novel")?.get()?.addOnCompleteListener {
            if (it.isSuccessful) {
                for (dc in it.result) {
                    val song = dc.toObject(Book::class.java)
                    firestoreList.add(song)
                }

                novelsList.postValue(firestoreList)

            }

        }
    }

    fun getChildren() {
        val firestoreList: MutableList<Book> = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db?.collection("books")?.whereEqualTo("category", "children")?.get()
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    for (dc in it.result) {
                        val song = dc.toObject(Book::class.java)
                        firestoreList.add(song)
                    }

                    childrenList.postValue(firestoreList)

                }

            }
    }
}