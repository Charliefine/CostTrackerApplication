package com.example.costtrackerapplication.ui.login.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CategoryViewModel : ViewModel() {

    private val _text = MutableLiveData<ArrayList<String>>().apply {
        var items: ArrayList<String> = ArrayList()
        items?.add("Food");
        items?.add("Clothing");
        items?.add("Bills");
        value = items
    }
    val text: LiveData<ArrayList<String>> = _text

}