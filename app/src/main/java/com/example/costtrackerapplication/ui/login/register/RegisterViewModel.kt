package com.example.costtrackerapplication.ui.login.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.costtrackerapplication.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RegisterViewModel : ViewModel()  {

    private lateinit var database : DatabaseReference

    fun registerUser(
        registerUsernameInput: String?,
        registerEmailInput: String?,
        registerPasswordInput: String?
    ): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(registerEmailInput!!.toString(), registerPasswordInput!!.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val newUserUid: String = task.result?.user?.uid.toString()
                    Log.i("Lifecycle", "Successfully created account")
                    result.postValue(true)
                    /**
                     * TODO
                     * Set DisplayName to new users
                    setUsername(newUserUid, registerUsernameInput)
                    */
                    addToDatabase(newUserUid, registerEmailInput, registerUsernameInput)
                } else {
                    Log.i("Lifecycle", "Something went wrong during creating account")
                    result.postValue(false)
                }
            }
        return result
    }

    private fun setUsername(newUserUid: String?, registerUsernameInput: String?) {

    }

    private fun addToDatabase(
        newUserUid: String?,
        registerEmailInput: String,
        registerUsernameInput: String?
    ) {
        //Database reference
        val currentDate: String = setDate()
        database = FirebaseDatabase.getInstance().getReference("Users")

        val newUser = User(newUserUid, registerUsernameInput, registerEmailInput, currentDate)

        database.child(newUserUid!!).child("UserInfo").setValue(newUser).addOnSuccessListener {
            Log.i("Lifecycle", "Successfully added to database")
        }.addOnFailureListener {
            Log.i("Lifecycle", "Unsuccessfully added to database")
        }
    }

    private fun setDate(): String {
        //Current date
        val currentDate = LocalDateTime.now()
        val formatterDate = DateTimeFormatter.ofPattern("dd MMM yyyy")
        return currentDate.format(formatterDate)
    }
}