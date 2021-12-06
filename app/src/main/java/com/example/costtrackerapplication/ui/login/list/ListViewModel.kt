package com.example.costtrackerapplication.ui.login.list

import com.example.costtrackerapplication.model.FirebaseDatabaseRepository
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
import kotlin.collections.ArrayList

private lateinit var database : DatabaseReference
private lateinit var itemArrayList : ArrayList<Item>
private var sortDesc: Boolean = true

private var itemsCopy: ArrayList<Item> = ArrayList()

class ListViewModel : ViewModel() {

    private val repository = FirebaseDatabaseRepository()
/*

    private val _newsFeedLiveData = MutableLiveData<ArrayList<Item>>().apply {
        //value = repository.fetchItems().value!!
        value = repository.fetchItems().value!!
*/
/*        postValue(repository.fetchItems().value?.let { ItemAdapter(it) })
        Log.i("Lifecycle", repository.fetchItems().value?.toString()!!)*//*

    }
    val newsFeedLiveData: LiveData<ArrayList<Item>> = _newsFeedLiveData
*/

    private val _newsFeedLiveData = MutableLiveData<ArrayList<Item>>()
    val newsFeedLiveData: LiveData<ArrayList<Item>> = _newsFeedLiveData

    fun fetchItems() {
        repository.fetchItems(_newsFeedLiveData)
    }


    private val _itemArrayListAdapter = MutableLiveData<ItemAdapter>().apply{
        itemArrayList = arrayListOf()

        val uid: String = FirebaseAuth.getInstance().currentUser?.uid.toString()

        database = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Items")

        database.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemArrayList.clear()
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        //TYLKO DATA
                        //if(userSnapshot.child("date").value.toString()=="29 lis 2021"){
                            // PRZEDZIAŁ DAT
                        //if(userSnapshot.child("date").value.toString()<="29 lis 2021"&&userSnapshot.child("date").value.toString()>="28 lis 2021"){//##
                        val item = userSnapshot.getValue(Item::class.java)
                        itemArrayList.add(item!!)

                        val format = SimpleDateFormat("dd.MM.yyyy")

                            //Sort
                            if(sortDesc) {
                                itemArrayList.sortWith { o1, o2 ->
                                    format.parse(o2.date)
                                        ?.compareTo(format.parse(o1.date)!!)!!
                                }
                            }else{
                                itemArrayList.sortWith { o1, o2 ->
                                    format.parse(o1.date)
                                        ?.compareTo(format.parse(o2.date)!!)!!
                                }
                            }
                    }
                }
                postValue(ItemAdapter(itemArrayList))
                Log.i("Lifecycle", "Printed items!")
                itemsCopy = itemArrayList
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    var itemArrayListAdapter: LiveData<ItemAdapter> = _itemArrayListAdapter


    fun onRefresh(swipeToRefresh: SwipeRefreshLayout) {
        swipeToRefresh.setOnRefreshListener {
            swipeToRefresh.isRefreshing = false
        }
    }

    fun refreshOnFilter(swipeToRefresh: SwipeRefreshLayout) {
        swipeToRefresh.isRefreshing = false
        _itemArrayListAdapter.postValue(ItemAdapter(itemArrayList))
    }

    fun getFilter(text: String) {
        ItemAdapter(itemsCopy).filter.filter(text)
        itemArrayList = ItemAdapter(itemsCopy).getFilteredResults(text)
    }

    fun setSort(){
        sortDesc = !sortDesc
    }

    fun checkSort(): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        if (sortDesc) result.postValue(true)
        else result.postValue(false)
        return result
    }
}