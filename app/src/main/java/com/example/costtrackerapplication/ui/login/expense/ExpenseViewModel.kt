package com.example.costtrackerapplication.ui.login.expense

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.costtrackerapplication.model.Item
import com.example.costtrackerapplication.model.ItemAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat

private lateinit var filterFirstDate: String
private lateinit var filterSecondDate: String

private lateinit var database : DatabaseReference
private lateinit var itemArrayList : ArrayList<Item>
private var itemsCopy: ArrayList<Item> = ArrayList()
private var clickedCategory = ""

class ExpenseViewModel : ViewModel() {

    private var sumExpense: Float = 0.0F
    private var sumExpense2: Float = 0.0F
    private val format = SimpleDateFormat("dd.MM.yyyy")
    private val mapSum = mutableMapOf<String, Float>()


    fun setDateRange(firstDate: String, secondDate: String){
        filterFirstDate = firstDate
        filterSecondDate = secondDate
    }

    private val _summaryExpense = MutableLiveData<String>().apply {
        val uid: String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val unionList: ArrayList<Map<String, Float>> = arrayListOf()

        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Items")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    sumExpense = 0.0F
                    if(snapshot.exists()){
                        for(userSnapshot in snapshot.children){
                            if(format.parse(userSnapshot.child("date").value.toString()) in format.parse(filterFirstDate)..format.parse(filterSecondDate)) {
                                val amountFloat: String =
                                    userSnapshot.child("amount").getValue(String::class.java)!!
                                val category: String =
                                    userSnapshot.child("category").getValue(String::class.java)!!
                                val amountPrec: Float = amountFloat.toFloat()
                                var map =  mutableMapOf<String, Float>()
                                sumExpense += amountPrec
                                map[category] = amountPrec
                                unionList.add(map)
                            }
                        }
                    }
                    val sumExpenseRounded = String.format("%.2f", sumExpense)
                    value = sumExpenseRounded
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
    val summaryExpense: LiveData<String> = _summaryExpense

    private val _categoriesExpense = MutableLiveData<Map<String, Float>>().apply {
        val uid: String = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val unionList: ArrayList<Map<String, Float>> = arrayListOf()

        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Items")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    sumExpense2 = 0.0F
                    unionList.clear()
                    mapSum.clear()

                    if(snapshot.exists()){
                        for(userSnapshot in snapshot.children){
                            if(format.parse(userSnapshot.child("date").value.toString()) in format.parse(filterFirstDate)..format.parse(filterSecondDate)) {
                                val amountFloat: String =
                                    userSnapshot.child("amount").getValue(String::class.java)!!
                                val category: String =
                                    userSnapshot.child("category").getValue(String::class.java)!!
                                val amountPrec: Float = amountFloat.toFloat()
                                var map =  mutableMapOf<String, Float>()
                                sumExpense2 += amountPrec
                                map[category] = amountPrec
                                unionList.add(map)
                            }
                        }
                    }
                    unionList.forEach {
                        Log.i("Lifecycle3", "${it.keys} + ${it.values}")
                        var sum = mapSum[it.keys.toString()]
                        if (sum == null) sum = 0f
                        if (mapSum[it.keys.toString()] == null) mapSum[it.keys.toString()] = it.values.first()
                        var sumRound = sum + it.values.first()
                        sumRound = String.format("%.2f", sumRound).toFloat()
                        mapSum[it.keys.toString()] = sumRound
                    }
                    value = mapSum
                    Log.i("Lifecycle1", mapSum.toString())
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
    val categoriesExpense: LiveData<Map<String, Float>> = _categoriesExpense

    private val _itemArrayListAdapter = MutableLiveData<ItemAdapter>().apply{
        itemArrayList = arrayListOf()

        val uid: String = FirebaseAuth.getInstance().currentUser?.uid.toString()

        database = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Items")

        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemArrayList.clear()
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        if(clickedCategory == "") {
                            if(format.parse(userSnapshot.child("date").value.toString()) in format.parse(filterFirstDate)..format.parse(filterSecondDate)) {
                                val item = userSnapshot.getValue(Item::class.java)
                                itemArrayList.add(item!!)

                                val format = SimpleDateFormat("dd.MM.yyyy")

                                //Sort
                                itemArrayList.sortWith { o1, o2 ->
                                    format.parse(o2.date!!)
                                        ?.compareTo(format.parse(o1.date!!))!!
                                }
                            }
                        }else{
                            if (format.parse(userSnapshot.child("date").value.toString()) in format.parse(filterFirstDate)..format.parse(filterSecondDate) && userSnapshot.child("category").value.toString() == clickedCategory) {
                                val item = userSnapshot.getValue(Item::class.java)
                                itemArrayList.add(item!!)

                                val format = SimpleDateFormat("dd.MM.yyyy")

                                //Sort
                                itemArrayList.sortWith { o1, o2 ->
                                    format.parse(o2.date!!)
                                        ?.compareTo(format.parse(o1.date!!))!!
                                }
                            }
                        }
                    }
                }
                postValue(ItemAdapter(itemArrayList))
                itemsCopy = itemArrayList
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    var itemArrayListAdapter: LiveData<ItemAdapter> = _itemArrayListAdapter

    fun getFilter(text: String) {
        ItemAdapter(itemsCopy).filter.filter(text)
        itemArrayList = ItemAdapter(itemsCopy).getFilteredResults(text)
    }

    fun refreshOnFilter(swipeToRefresh: SwipeRefreshLayout) {
        swipeToRefresh.isRefreshing = false
        _itemArrayListAdapter.postValue(ItemAdapter(itemArrayList))
    }

    fun onRefresh(swipeToRefresh: SwipeRefreshLayout) {
        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.isRefreshing = false
        }
    }

    fun setCategory(bundleFromFragment: String?) {
        clickedCategory = bundleFromFragment!!
    }
}