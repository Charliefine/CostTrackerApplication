package com.example.costtrackerapplication.ui.login.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.costtrackerapplication.model.FirebaseDatabaseRepository
import com.example.costtrackerapplication.model.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailsViewModel(mItemID: String) : ViewModel() {



    private val repository = FirebaseDatabaseRepository()


    private val _itemDetails = MutableLiveData<Item>().apply {
        val uid: String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Items").child(mItemID)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val title = task.result.child("title").value.toString()
                    val amount = task.result.child("amount").value.toString()
                    val description = task.result.child("description").value.toString()
                    val category = task.result.child("category").value.toString()
                    val date = task.result.child("date").value.toString()
                    val addedDate = task.result.child("addedDate").value.toString()
                    value = Item("", title, amount, date, addedDate, category, description)
                }
            }
    }
    val itemDetails: LiveData<Item> = _itemDetails



}

class MyDetailsViewModelFactory(private val mItemID: String) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(mItemID) as T
    }
}