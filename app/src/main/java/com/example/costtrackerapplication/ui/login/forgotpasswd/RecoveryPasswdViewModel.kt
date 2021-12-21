package com.example.costtrackerapplication.ui.login.forgotpasswd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoveryPasswdViewModel : ViewModel() {

    fun sendRecoveryPassword(recoveryPasswdEmailInput: String?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        Firebase.auth.sendPasswordResetEmail(recoveryPasswdEmailInput!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) result.postValue(true)
                else result.postValue(false)
            }
        return result
    }
}