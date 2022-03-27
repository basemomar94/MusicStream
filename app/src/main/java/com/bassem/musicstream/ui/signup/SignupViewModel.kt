package com.bassem.musicstream.ui.signup

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bassem.musicstream.entities.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupViewModel(app: Application) : AndroidViewModel(app) {
    val context = app.applicationContext
    var auth: FirebaseAuth? = null
    var db: FirebaseFirestore? = null
    var userId = MutableLiveData<String>()
    var isSucceed = MutableLiveData<Boolean>()


    fun auth(mail: String, password: String) {
        auth = FirebaseAuth.getInstance()
        auth?.createUserWithEmailAndPassword(mail, password)?.addOnCompleteListener {
            if (it.isSuccessful) {
                userId.postValue(auth?.currentUser?.uid)

            } else {
                Toast.makeText(context, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                isSucceed.postValue(false)
            }

        }
    }

    fun addUsertoFirebase(user: User, userid: String) {
        db = FirebaseFirestore.getInstance()
        db?.collection("users")?.document(userid)?.set(user)?.addOnCompleteListener {
            if (it.isSuccessful) {
                isSucceed.postValue(it.isSuccessful)
            } else {
                Toast.makeText(context, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                isSucceed.postValue(false)


            }
        }

    }


}