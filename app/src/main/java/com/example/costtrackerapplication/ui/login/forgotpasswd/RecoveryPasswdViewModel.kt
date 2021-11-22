package com.example.costtrackerapplication.ui.login.forgotpasswd

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RecoveryPasswdViewModel : ViewModel() {
    private lateinit var database : DatabaseReference
    private lateinit var databaseUser : DatabaseReference

    fun sendRecoveryPassword(recoveryPasswdEmailInput: String?): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        var emailFromDatabase: String

        Firebase.auth.sendPasswordResetEmail(recoveryPasswdEmailInput!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) result.postValue(true)
                else result.postValue(false)
            }
/*
        database = FirebaseDatabase.getInstance().getReference("Users")
        database.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot = task.result
                for(userSnapshot in snapshot?.children!!){
                    val uid: String = userSnapshot.toString()
                    databaseUser = database.child(uid).child("UserInfo")
                    databaseUser.get().addOnCompleteListener { task ->
                        val snapshot = task.result
                        if(snapshot?.child("email").toString() == recoveryPasswdEmailInput) result.postValue(true)
                        else result.postValue(false)
                    }
                    *//*emailFromDatabase = snapshot?.children.toString()
                    if(recoveryPasswdEmailInput == emailFromDatabase){
                        result.postValue(true)
                        break
                    }else{
                        result.postValue(false)
                    }*//*
                }
            } else {
                Log.i("Lifecycle", "Something went wrong with retrieving data")
            }

        }*/

        return result
    }

}