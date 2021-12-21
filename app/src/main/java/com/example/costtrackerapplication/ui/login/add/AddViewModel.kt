package com.example.costtrackerapplication.ui.login.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.costtrackerapplication.model.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.absoluteValue

private lateinit var firebaseAuth: FirebaseAuth
private lateinit var database : DatabaseReference

class AddViewModel : ViewModel() {
    fun addExpense(
        addTitleInput: String?,
        addAmountInput: String?,
        addDescriptionInput: String?,
        addCategory: String?,
        addDate: String?,
        currentDate: String
    ): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()

        database = FirebaseDatabase.getInstance().getReference("Users")
        val uid: String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val xd: Float = addAmountInput?.toFloat()?.absoluteValue!!
        val addAmountInputFixed: String = xd.toString()

        //Dynamic id
        val key = database.child("Users").child(uid).child("Items").push().key
        val newItem = Item(key, addTitleInput, addAmountInputFixed, addDate, currentDate, addCategory, addDescriptionInput)

        if (key != null) {
            database.child(uid).child("Items").child(key).setValue(newItem).addOnSuccessListener {
                result.postValue(true)
            }.addOnFailureListener {
                result.postValue(false)
            }
        }
        return result
    }
}