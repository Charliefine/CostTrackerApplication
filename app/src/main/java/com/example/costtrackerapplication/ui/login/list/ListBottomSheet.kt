package com.example.costtrackerapplication.ui.login.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.ListBottomSheetFragmentBinding
import com.example.costtrackerapplication.databinding.ListFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.ArrayList

class ListBottomSheet : BottomSheetDialogFragment() {

    private lateinit var listViewModel: ListViewModel
    private var _binding: ListBottomSheetFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding
        _binding = ListBottomSheetFragmentBinding.inflate(inflater, container, false)

        //ViewModel
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        Log.i("Lifecycle", "XD")

/*
        binding.btnBottomSheetClose.setOnClickListener {
            Log.i("Lifecycle", "Closed")
            this.dialog?.dismiss()
        }
*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var filterList: ArrayList<String>? = ArrayList()
        requireActivity().supportFragmentManager.setFragmentResultListener("filterListKey2", this ) { _, bundle ->
            val resultReceived = bundle.getStringArrayList("bundleList2")
            filterList = resultReceived
            Log.i("Lifecycle3", filterList.toString())
        }

        binding.btnBottomSheetClose.setOnClickListener {

            //Filter list
            requireActivity().supportFragmentManager.setFragmentResult("filterListKey1", bundleOf("bundleList1" to filterList))
            Log.i("Lifecycle4", filterList.toString())
        }

    }

}