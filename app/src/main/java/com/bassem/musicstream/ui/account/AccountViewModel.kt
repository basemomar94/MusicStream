package com.bassem.musicstream.ui.account

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bassem.musicstream.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountViewModel(app: Application) : AndroidViewModel(app) {
    val context = app.applicationContext
    var db = FirebaseFirestore.getInstance()
    var isUpdated = MutableLiveData<Boolean>()
    private var currentuser = FirebaseAuth.getInstance().currentUser?.uid
    var user = MutableLiveData<User>()
    fun getUserInfo() {
        db.collection("users").document(currentuser!!).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val userFirebase = it.result.toObject(User::class.java)
                user.postValue(userFirebase!!)
            }
        }

    }

    fun updateInfo(updates: HashMap<String, Any>) {
        db.collection("users").document(currentuser!!).update(updates).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Your info has been updated", Toast.LENGTH_SHORT).show()
                isUpdated.postValue(true)
            }
        }
    }
}