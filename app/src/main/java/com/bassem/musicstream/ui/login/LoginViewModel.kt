package com.bassem.musicstream.ui.login

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(app: Application) : AndroidViewModel(app) {
    var auth: FirebaseAuth? = null
    val appcontext = app.applicationContext
    var isLogin = MutableLiveData<Boolean>()
    fun login(mail: String, password: String) {
        auth = FirebaseAuth.getInstance()
        auth?.signInWithEmailAndPassword(mail, password)?.addOnCompleteListener {
            if (it.isSuccessful) {

                isLogin.postValue(true)

            }
        }?.addOnFailureListener {
            Toast.makeText(appcontext, "${it.message}", Toast.LENGTH_SHORT).show()
            isLogin.postValue(false)
        }
    }
}