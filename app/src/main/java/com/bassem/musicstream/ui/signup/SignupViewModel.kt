package com.bassem.musicstream.ui.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bassem.musicstream.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupViewModel(app: Application) : AndroidViewModel(app) {
    var auth: FirebaseAuth? = null
    var db: FirebaseFirestore? = null
    var userId = MutableLiveData<String>()
    var succed = MutableLiveData<Boolean>()


    fun auth(mail: String, password: String) {
        auth = FirebaseAuth.getInstance()
        auth?.createUserWithEmailAndPassword(mail, password)?.addOnCompleteListener {
            if (it.isSuccessful) {
                userId.postValue(auth?.currentUser?.uid)

            }

        }
    }

    fun addUsertoFirebase(user: User, userid: String) {
        db = FirebaseFirestore.getInstance()
        db?.collection("users")?.document(userid)?.set(user)?.addOnCompleteListener {
            if (it.isSuccessful) {
                succed.postValue(it.isSuccessful)
            }
        }

    }


}