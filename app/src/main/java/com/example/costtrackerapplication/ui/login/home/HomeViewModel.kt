package com.example.costtrackerapplication.ui.login.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.costtrackerapplication.model.FirebaseDatabaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat


private lateinit var filterFirstDate: String
private lateinit var filterSecondDate: String

class HomeMainViewModel : ViewModel() {

    private val repository = FirebaseDatabaseRepository()

    private var sumExpense: Float = 0.0F
    private var costLimitDb: String = ""
    private val format = SimpleDateFormat("dd.MM.yyyy")

    fun setDateRange(firstDate: String, secondDate: String){
        filterFirstDate = firstDate
        filterSecondDate = secondDate
    }

    fun setNewLimit(newLimit: String) {
        repository.changeLimit(newLimit)
    }

    private val _costLimit = MutableLiveData<String>().apply {
        val uid: String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        //var limit: String

        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("UserInfo")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        for(userSnapshot in snapshot.children){
                            costLimitDb = userSnapshot.value.toString()
                        }
                    }
                    value = costLimitDb
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
/*        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("UserInfo").child("limit")
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    postValue(task.result.value.toString())
                }
            }*/
    }
    val costLimit: LiveData<String> = _costLimit

    private val _costLimit2 = MutableLiveData<String>()
    val costLimit2: LiveData<String> = _costLimit2

    fun getLimit() {
        repository.fetchLimit(_costLimit)
    }

    private val _welcomeName = MutableLiveData<String>().apply {
        value = getInstance().currentUser?.displayName
        Log.i("Lifecycle", value.toString())
        Log.i("Lifecycle2", getInstance().currentUser?.email.toString())
    }
    val welcomeName: LiveData<String> = _welcomeName

    private val _summaryExpense = MutableLiveData<String>().apply {
        val uid: String = getInstance().currentUser?.uid.toString()
            FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Items")
                .addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        sumExpense = 0.0F
                        if(snapshot.exists()){
                            for(userSnapshot in snapshot.children){
                                if(format.parse(userSnapshot.child("date").value.toString()) in format.parse(filterFirstDate)..format.parse(filterSecondDate)) {
                                    val amountFloat: String =
                                        userSnapshot.child("amount")?.getValue(String::class.java)!!
                                    val amountPrec: Float? = amountFloat.toFloat()
                                    if (amountPrec != null) {
                                        sumExpense += amountPrec
                                    }
                                }
                            }
                        }
                        value = sumExpense.toString()
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
    }
    val summaryExpense: LiveData<String> = _summaryExpense
}