package com.example.costtrackerapplication.model

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.costtrackerapplication.R

class CategorySummaryAdapter(private val context: Activity, private val categoryName: ArrayList<String>, private val categoryAmount: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.expense_summary_list_row, categoryName) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.expense_summary_list_row, null, true)

        val category = rowView.findViewById(R.id.expense_summary_listview_category) as TextView
        val amount = rowView.findViewById(R.id.expense_summary_listview_amount) as TextView
        val icon = rowView.findViewById(R.id.expense_summary_listview_icon) as ImageView

        //Setting icons
        var mapSum = mutableMapOf<String, Int>()
        mapSum["Food"] = R.drawable.ic_round_fastfood_24
        mapSum["Clothing"] = R.drawable.ic_round_checkroom_24
        mapSum["Bills"] = R.drawable.ic_round_receipt_24
        mapSum["Entertainment"] = R.drawable.ic_round_celebration_24
        mapSum["Sports"] = R.drawable.ic_round_sports_baseball_24
        mapSum["Home"] = R.drawable.ic_round_house_24
        mapSum["Car"] = R.drawable.ic_round_directions_car_24
        mapSum["Pets"] = R.drawable.ic_round_pets_24
        mapSum["Transport"] = R.drawable.ic_round_directions_bus_24
        mapSum["Others"] = R.drawable.ic_round_lightbulb_24

        category.text = categoryName[position]
        amount.text = categoryAmount[position] + " PLN"

        mapSum.forEach {
            if(it.key == category.text){
                icon.setImageResource(it.value)
            }
        }


        return rowView
    }
}