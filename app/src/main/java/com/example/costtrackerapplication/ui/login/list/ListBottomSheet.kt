package com.example.costtrackerapplication.ui.login.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.costtrackerapplication.databinding.ListBottomSheetFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.ArrayList

//TODO UNUSED

class ListBottomSheet : BottomSheetDialogFragment() {

    private lateinit var listViewModel: ListViewModel
    private var _binding: ListBottomSheetFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var filterList: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Binding
        _binding = ListBottomSheetFragmentBinding.inflate(inflater, container, false)
        Log.i("Lifecycle", "XD")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        //ViewModel
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        listViewModel.checkFilters.observe(viewLifecycleOwner, {
            Log.i("LifecycleBottomSheet", it.toString())
        })
*/

        filterList.add("XD")
        listViewModel.setFilters(filterList)
/*
        var filterList: ArrayList<String>? = ArrayList()
        requireActivity().supportFragmentManager.setFragmentResultListener("filterListKey2", this ) { _, bundle ->
            val resultReceived = bundle.getStringArrayList("bundleList2")
            filterList = resultReceived
            Log.i("Lifecycle3", filterList.toString())
        }
*/

/*
        binding.btnBottomSheetClose.setOnClickListener {

            //Filter list
            requireActivity().supportFragmentManager.setFragmentResult("filterListKey1", bundleOf("bundleList1" to filterList))
            Log.i("Lifecycle4", filterList.toString())

            val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.Theme_Design_BottomSheetDialog)
            bottomSheetDialog.dismissWithAnimation
        }
*/

    }

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
        Log.i("LifecycleBottomSheet", "Created")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        listViewModel.setFilters(filterList)
    }

}