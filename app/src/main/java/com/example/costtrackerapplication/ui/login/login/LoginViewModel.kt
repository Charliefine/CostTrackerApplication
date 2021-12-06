package com.example.costtrackerapplication.ui.login.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.costtrackerapplication.MainActivity
import com.example.costtrackerapplication.model.FirebaseDatabaseRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel: ViewModel(){

    private lateinit var firebaseAuth: FirebaseAuth
    private val repository = FirebaseDatabaseRepository()

    fun loginUserWithEmail(loginEmailInput: String?, loginPasswordInput: String?, context: Context?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(loginEmailInput!!, loginPasswordInput!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.postValue(true)
                } else {
                    result.postValue(false)
                }
            }
        return result
    }

    fun isLogged(): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        firebaseAuth = FirebaseAuth.getInstance()
        //configureGoogleSignIn()

        if (FirebaseAuth.getInstance().currentUser != null) result.postValue(true)
        else result.postValue(false)

        return result
    }
/*
    fun firebaseAuthWithGoogle(idToken: String, mainActivity: MainActivity) {
        firebaseAuth = FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(mainActivity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Lifecycle", "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Lifecycle", "signInWithCredential:failure", task.exception)
                }
            }
    }*/
    fun firebaseAuthWithGoogle(idToken: String) : LiveData<Boolean> {
    val result = MutableLiveData<Boolean>()
        firebaseAuth = FirebaseAuth.getInstance()
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if(task.result.additionalUserInfo?.isNewUser!!) {
                        repository.addLimit(task.result.user?.uid.toString())
                    }else{
                    }
                    // Sign in success, update UI with the signed-in user's information
                    result.postValue(true)
                    Log.i("Info", "signInWithCredential:success")
                } else {
                    // If sign in fails, display a message to the user.
                    result.postValue(false)
                    Log.i("Info", "signInWithCredential:failure", task.exception)
                }
            }
    return result
    }
}