package com.example.costtrackerapplication.ui.login.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.costtrackerapplication.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel: ViewModel(){

    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    fun loginUserWithEmail(loginEmailInput: String?, loginPasswordInput: String?, context: Context?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(loginEmailInput!!, loginPasswordInput!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    result.postValue(true)
                } else {
                    result.postValue(false)
/*                    binding.loginPasswordLayout.error =
                        "Username or password is incorrect"*/
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
    }

}