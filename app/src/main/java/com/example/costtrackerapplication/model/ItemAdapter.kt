package com.example.costtrackerapplication.model

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.activities.DetailsActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class ItemAdapter(private var itemList : ArrayList<Item>) : RecyclerView.Adapter<ItemAdapter.MyViewHolder>(),
    Filterable {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val titleItem : TextView = itemView.findViewById(R.id.recyclerview_title)
        val categoryItem : TextView = itemView.findViewById(R.id.recyclerview_category)
        val amountItem : TextView = itemView.findViewById(R.id.recyclerview_amount)
        val dateItem : TextView = itemView.findViewById(R.id.recyclerview_date)
        val deleteBtn : Button = itemView.findViewById(R.id.btn_delete_row)
        val cardViewRow : MaterialCardView = itemView.findViewById(R.id.recyclerview_row_card)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recyclerview_row,
            parent,false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = itemList[holder.adapterPosition]
        val context = holder.itemView.context
        holder.titleItem.text = currentItem.title
        holder.categoryItem.text = currentItem.category
        holder.amountItem.text = currentItem.amount + " PLN"
        holder.dateItem.text = currentItem.date

        val itemID: String = itemList[holder.adapterPosition].id.toString()
        val itemName: String = itemList[holder.adapterPosition].title.toString()

        holder.deleteBtn.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.deleteBtn)
            popupMenu.menuInflater.inflate(R.menu.menu_recyclerview_row, popupMenu.menu)
                        popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_rv_row_view -> {
                        val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
                        val b = Bundle()
                        b.putString("itemID", itemID)
                        intent.putExtras(b)
                        holder.itemView.context.startActivity(intent)
                    }
                    R.id.menu_rv_row_delete ->
                        MaterialAlertDialogBuilder(context,
                            R.style.ThemeOverlay_MaterialComponents_Dark)
                            .setTitle("Deleting expense")
                            .setMessage("Are you sure you want to delete expense \n$itemName?")
                            .setNegativeButton("Cancel") { dialog, _ ->
                                dialog.cancel()
                            }
                            .setPositiveButton("Yes") { _, _ ->
                                //Timer
                                val timer = Timer()
                                timer.schedule(4000){
                                    FirebaseDatabaseRepository().deleteItem(itemID)
                                }

                                //Undo SnackBar
                                Snackbar.make(holder.itemView, "Successfully deleted: $itemName", Snackbar.LENGTH_SHORT)
                                    .setAction("Undo") {
                                        timer.cancel()
                                    }
                                    .show()
                            }
                            .show()
                }
                true
            }
            popupMenu.show()
        }

        holder.cardViewRow.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            val b = Bundle()
            b.putString("itemID", itemID)
            intent.putExtras(b)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredResults: ArrayList<Item> = if (constraint!!.isEmpty()) {
                    itemList
                } else {
                    getFilteredResults(
                        constraint.toString()
                    )
                }
                val results = FilterResults()
                results.values = filteredResults
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                itemList = results.values as ArrayList<Item>
                notifyDataSetChanged()
            }
        }
    }

    fun getFilteredResults(constraint: CharSequence): ArrayList<Item> {
        val results: ArrayList<Item> = ArrayList()
        for (item in itemList) {
            if (item.title?.lowercase(Locale.getDefault())?.contains(constraint) == true) {
                results.add(item)
            }
        }
        return results
    }


}