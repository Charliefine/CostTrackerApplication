package com.example.costtrackerapplication.ui.login.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.costtrackerapplication.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private var registerDate: String = ""

class ProfileViewModel : ViewModel() {

    private val _getProfileDetails = MutableLiveData<Profile>().apply {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        val username = currentUser?.displayName
        val email = currentUser?.email
        val photoURL = currentUser?.photoUrl.toString()
        val addedDate = getRegisterDate(uid!!)

        value = Profile(username, email, photoURL, addedDate)

    }
    val getProfileDetails: LiveData<Profile> = _getProfileDetails

    private fun getRegisterDate(uid: String): String {
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("UserInfo")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        registerDate = snapshot.child("createDate").value.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        return registerDate
    }
}