package com.example.costtrackerapplication.model

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.costtrackerapplication.R

class CategorySummaryAdapter(private val context: Activity, private val categoryName: ArrayList<String>, private val categoryAmount: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.expense_summary_list_row, categoryName) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.expense_summary_list_row, null, true)

        val category = rowView.findViewById(R.id.expense_summary_listview_category) as TextView
        val amount = rowView.findViewById(R.id.expense_summary_listview_amount) as TextView

        category.text = categoryName[position]
        amount.text = categoryAmount[position]

        return rowView
    }
}