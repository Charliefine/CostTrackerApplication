package com.example.costtrackerapplication.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class FirebaseDatabaseRepository {

    private val uid: String = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private val databaseReferenceUid =
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Items")

    val itemArrayList = arrayListOf<Item>()
    var fetchedLimit: String = ""
    private lateinit var item: Item

    fun fetchItems(_newsFeedLiveData: MutableLiveData<ArrayList<Item>>) {
        databaseReferenceUid
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    itemArrayList.clear()
                    for (userSnapshot in snapshot.children) {
                        val item = userSnapshot.getValue(Item::class.java)
                        itemArrayList.add(item!!)
                        Log.i("Lifeycle", itemArrayList.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Nothing to do
                }
            })
        _newsFeedLiveData.postValue(itemArrayList)
    }

    fun deleteItem(itemID: String) {
        val query: Query = databaseReferenceUid.child(itemID)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.ref.removeValue()
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun addLimit(uidNew: String) {
        val newLimit = Limit("2500")
        FirebaseDatabase.getInstance().getReference("Users").child(uidNew).child("UserInfo").setValue(newLimit).addOnSuccessListener {
                Log.i("Lifecycle", "Successfully added limit to database")
            }.addOnFailureListener {
                Log.i("Lifecycle", "Unsuccessfully added limit to database")
            }
    }

    fun changeLimit(newLimit: String) {
        val firebaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("UserInfo")
        val query: Query = firebaseReference
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                firebaseReference.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result?.child("title")?.ref?.removeValue()

                        val newLimit = Limit(newLimit)
                        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("UserInfo").setValue(newLimit).addOnSuccessListener {
                            Log.i("Lifecycle", "Successfully added limit to database")
                        }.addOnFailureListener {
                            Log.i("Lifecycle", "Unsuccessfully added limit to database")
                        }
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    fun fetchLimit(_costLimit: MutableLiveData<String>) {
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("UserInfo")
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fetchedLimit = task.result.child("limit").value.toString()
                    Log.i("Lifecycle", task.result.child("limit").value.toString())
                }
            }
        _costLimit.postValue(fetchedLimit)
    }
/*
    fun getItem(itemID: String): MutableLiveData<Item> {
        item = Item("", "", "", "", "", "", "")
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Items").child(itemID)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val title = task.result.child("title").value.toString()
                    val amount = task.result.child("amount").value.toString()
                    val description = task.result.child("description").value.toString()
                    val category = task.result.child("category").value.toString()
                    val date = task.result.child("date").value.toString()
                    val addedDate = task.result.child("addedDate").value.toString()
                    item = Item("", title, amount, date, addedDate, category, description)
                }
            }

    }*/
}