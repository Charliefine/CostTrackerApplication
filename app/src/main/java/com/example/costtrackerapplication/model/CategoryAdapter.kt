package com.example.costtrackerapplication.model

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.costtrackerapplication.R

class CategoryAdapter(private val context: Activity, private val categoryName: ArrayList<String>, private val categoryIcon: ArrayList<Int>)
    : ArrayAdapter<String>(context, R.layout.category_list_row, categoryName) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.category_list_row, null, true)

        val category = rowView.findViewById(R.id.category_listview_category) as TextView
        val icon = rowView.findViewById(R.id.category_listview_category_icon) as ImageView

        category.text = categoryName[position]
        icon.setImageResource(categoryIcon[position])

        return rowView
    }
}