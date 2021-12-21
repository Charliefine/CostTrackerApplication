package com.example.costtrackerapplication.ui.login.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.costtrackerapplication.R


class CategoryViewModel : ViewModel() {

    private val _text = MutableLiveData<ArrayList<String>>().apply {
        var items: ArrayList<String> = ArrayList()
        items.add("Food");
        items.add("Clothing");
        items.add("Bills");
        value = items
    }
    val text: LiveData<ArrayList<String>> = _text

    private val _categories = MutableLiveData<Pair<ArrayList<String>, ArrayList<Int>>>().apply {
        var categories: ArrayList<String> = ArrayList()
        categories.add("Food");
        categories.add("Clothing");
        categories.add("Bills");

        var icons: ArrayList<Int> = ArrayList()
        icons.add(R.drawable.ic_round_fastfood_24);
        icons.add(R.drawable.ic_round_checkroom_24);
        icons.add(R.drawable.ic_round_receipt_24);

        value = Pair(categories, icons)
    }
    val categories: LiveData<Pair<ArrayList<String>, ArrayList<Int>>> = _categories
}