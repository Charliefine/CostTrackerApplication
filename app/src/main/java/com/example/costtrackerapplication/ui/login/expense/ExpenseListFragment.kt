package com.example.costtrackerapplication.ui.login.expense

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.ExpenseListFragmentBinding
import java.util.*

class ExpenseListFragment : Fragment() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var itemRecyclerView : RecyclerView
    private var _binding: ExpenseListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Binding
        _binding = ExpenseListFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ViewModel
        expenseViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        //Set RecyclerView adadpter
        expenseViewModel.itemArrayListAdapter.observe(viewLifecycleOwner, {

            //onSaveInstanceState - saving recyclerview state
            val recyclerViewState = itemRecyclerView.layoutManager?.onSaveInstanceState()

            itemRecyclerView.adapter = it

            itemRecyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)

            //After successfully loaded items
            binding.expenseListSpinKit.isVisible = false
        })

        //SearchView
        binding.expenseListSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {
                expenseViewModel.getFilter(text.lowercase(Locale.getDefault()))
                return false
            }

            override fun onQueryTextChange(text: String): Boolean {
                expenseViewModel.getFilter(text.lowercase(Locale.getDefault()))
                expenseViewModel.refreshOnFilter(binding.expenseListSwipeToRefresh)
                return true
            }
        })

        //Recyclerview
        itemRecyclerView = view.findViewById(R.id.expense_list_recyclerView)!!
        itemRecyclerView.layoutManager = LinearLayoutManager(context)
        itemRecyclerView.setHasFixedSize(false)

        //Reset RecyclerView
        itemRecyclerView.recycledViewPool.clear()

        //Refresh
        expenseViewModel.onRefresh(binding.expenseListSwipeToRefresh)
    }

}