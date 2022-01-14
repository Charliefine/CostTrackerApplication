package com.example.costtrackerapplication.ui.login.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.costtrackerapplication.R


class CategoryViewModel : ViewModel() {

/*    private val _text = MutableLiveData<ArrayList<String>>().apply {
        var items: ArrayList<String> = ArrayList()
        items.add("Food");
        items.add("Clothing");
        items.add("Bills");
        value = items
    }
    val text: LiveData<ArrayList<String>> = _text*/

    private val _categories = MutableLiveData<Pair<ArrayList<String>, ArrayList<Int>>>().apply {
        var categories: ArrayList<String> = ArrayList()
        categories.add("Food")
        categories.add("Clothing")
        categories.add("Bills")
        categories.add("Entertainment")
        categories.add("Sports")
        categories.add("Home")
        categories.add("Car")
        categories.add("Pets")
        categories.add("Transport")
        categories.add("Others")

        var icons: ArrayList<Int> = ArrayList()
        icons.add(R.drawable.ic_round_fastfood_24)
        icons.add(R.drawable.ic_round_checkroom_24)
        icons.add(R.drawable.ic_round_receipt_24)
        icons.add(R.drawable.ic_round_celebration_24)
        icons.add(R.drawable.ic_round_sports_baseball_24)
        icons.add(R.drawable.ic_round_house_24)
        icons.add(R.drawable.ic_round_directions_car_24)
        icons.add(R.drawable.ic_round_pets_24)
        icons.add(R.drawable.ic_round_directions_bus_24)
        icons.add(R.drawable.ic_round_lightbulb_24)

        value = Pair(categories, icons)
    }
    val categories: LiveData<Pair<ArrayList<String>, ArrayList<Int>>> = _categories
}