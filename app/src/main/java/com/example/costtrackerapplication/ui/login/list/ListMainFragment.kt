package com.example.costtrackerapplication.ui.login.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.ListFragmentBinding
import com.example.costtrackerapplication.databinding.ListMainFragmentBinding
import com.example.costtrackerapplication.model.ItemAdapter
import java.lang.ref.WeakReference
import java.util.*

class ListMainFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private lateinit var itemRecyclerView : RecyclerView
    private var _binding: ListMainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Binding
        _binding = ListMainFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ViewModel
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        listViewModel.itemArrayListAdapter.observe(viewLifecycleOwner, {

            //onSaveInstanceState - saving recyclerview state
            val recyclerViewState = itemRecyclerView.layoutManager?.onSaveInstanceState()

            itemRecyclerView.adapter = it

            itemRecyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)

            //After successfully loaded items
            binding.listMainSpinKit.isVisible = false
            binding.addTextTitle.isVisible = false

            if (it.itemCount == 0) {
                binding.addTextTitle.isVisible = true
                binding.addTextTitle.text = "History not found. Add new expense to see all of history."
            }

        })

        //SearchView
        binding.listSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String): Boolean {
                listViewModel.getFilter(text.lowercase(Locale.getDefault()))
                return false
            }

            override fun onQueryTextChange(text: String): Boolean {
                listViewModel.getFilter(text.lowercase(Locale.getDefault()))
                listViewModel.refreshOnFilter(binding.swipeToRefresh)
                return true
            }
        })

        //Recyclerview
        itemRecyclerView = view.findViewById(R.id.recyclerView)!!
        itemRecyclerView.layoutManager = LinearLayoutManager(context)
        itemRecyclerView.setHasFixedSize(false)

        //Reset RecyclerView
        itemRecyclerView.recycledViewPool.clear()

        //Refresh
        listViewModel.onRefresh(binding.swipeToRefresh)
    }

}