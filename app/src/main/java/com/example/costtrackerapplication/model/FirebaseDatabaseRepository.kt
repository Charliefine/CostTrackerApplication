package com.example.costtrackerapplication.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class FirebaseDatabaseRepository {

    private val uid: String = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private val databaseReferenceUid =
        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Items")


    val itemArrayList = arrayListOf<Item>()
    private lateinit var fetchedLimit: String
    private var registerDate: String = ""
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
        val currentDate: String = setDate()
        val newGoogleUser = UserGoogle("2500", currentDate)
        FirebaseDatabase.getInstance().getReference("Users").child(uidNew).child("UserInfo").setValue(newGoogleUser).addOnSuccessListener {
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
                        val createDate = getCreateDate(uid)
                        val newLimitObject = UserGoogle(newLimit, createDate)
                        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("UserInfo").setValue(newLimitObject).addOnSuccessListener {
                            Log.i("Lifecycle", "Successfully changed limit to database")
                        }.addOnFailureListener {
                            Log.i("Lifecycle", "Unsuccessfully changed limit to database")
                        }
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    private fun getCreateDate(uid: String): String {
    FirebaseDatabase.getInstance().getReference("Users").child(uid).child("UserInfo")
        .addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    registerDate = String()
                    registerDate = snapshot.child("createDate").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    return registerDate
    }

    fun editExpense(item: Item){
        val firebaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Items")
        val query: Query = firebaseReference
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                firebaseReference.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val newItem = item
                        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Items").child(item.id.toString()).setValue(newItem).addOnSuccessListener {
                            Log.i("Lifecycle", "Successfully changed item in database")
                        }.addOnFailureListener {
                            Log.i("Lifecycle", "Unsuccessfully changed item in database")
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
                }
            }
        _costLimit.postValue(fetchedLimit)
    }

    private fun setDate(): String {

        //Current date
        val currentDate = LocalDateTime.now()
        val formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return currentDate.format(formatterDate)
    }
}