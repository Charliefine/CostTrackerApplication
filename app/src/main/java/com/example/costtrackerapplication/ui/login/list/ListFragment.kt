package com.example.costtrackerapplication.ui.login.list

import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.costtrackerapplication.DrawerActivity
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.ListBottomSheetFragmentBinding
import com.example.costtrackerapplication.databinding.ListFragmentBinding
import com.example.costtrackerapplication.model.ItemAdapter
import com.example.costtrackerapplication.ui.login.home.HomeMainFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import java.util.*
import kotlin.collections.ArrayList

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private lateinit var itemRecyclerView : RecyclerView
    private lateinit var selectedChipData: ArrayList<String>
    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private var _bindingBottomSheet: ListBottomSheetFragmentBinding? = null
    private val bindingBottomSheet get() = _bindingBottomSheet!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding
        _binding = ListFragmentBinding.inflate(inflater, container, false)

        //Binding
//        _bindingBottomSheet = ListBottomSheetFragmentBinding.inflate(inflater, container, false)
        _bindingBottomSheet = ListBottomSheetFragmentBinding.inflate(inflater, container, false)
/*

        //ViewModel
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
*/

        //Print list
        val fragmentManager: FragmentManager? = activity?.supportFragmentManager
        val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.list_frame_layout, ListMainFragment())?.commit()

        //itemRecyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)

/*
        //Listener to filter BottomSheet
        var filterList: ArrayList<String>? = ArrayList()
        requireActivity().supportFragmentManager.setFragmentResultListener("filterListKey1", this ) { _, bundle ->
            val resultReceived = bundle.getStringArrayList("bundleList1")
            filterList = resultReceived
            Log.i("Lifecycle1", filterList.toString())
        }
*/

        /////////////////////////////////////////////
/*        selectedChipData = ArrayList()

        val onCheckedChangeListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                selectedChipData.add(buttonView.text.toString())
            }else{
                selectedChipData.remove(buttonView.text.toString())
            }
        }
        view?.findViewById<Chip>(R.id.chip_1)?.setOnCheckedChangeListener(onCheckedChangeListener)
        bindingBottomSheet.chip2.setOnCheckedChangeListener(onCheckedChangeListener)*/
/////////////////////////////////////////////

        //Filters
        binding.btnBottomSheetFilters.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.Theme_Design_BottomSheetDialog)
//            val bottomSheetView: View = LayoutInflater.from(requireContext()).inflate(R.layout.list_bottom_sheet_fragment, view?.findViewById(R.id.list_bottom_sheet_container))
            val bottomSheetView: View = LayoutInflater.from(requireContext()).inflate(R.layout.list_bottom_sheet_fragment, bindingBottomSheet.listBottomSheetContainer)
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()

            bottomSheetDialog.setOnCancelListener {
                //(view?.findViewById(R.id.list_bottom_sheet_container) as ViewGroup?)?.removeView(view?.findViewById(R.id.list_bottom_sheet_container)!!)
                (bindingBottomSheet.root.parent as ViewGroup?)?.removeView(bindingBottomSheet.listBottomSheetContainer)
            }

            bindingBottomSheet.btnBottomSheetClose.setOnClickListener {
//            bottomSheetView.findViewById<MaterialButton>(R.id.btn_bottom_sheet_close).setOnClickListener {
//            Log.i("Lifecycle", selectedChipData.toString())
                (bindingBottomSheet.root.parent as ViewGroup?)?.removeView(bindingBottomSheet.listBottomSheetContainer)

                bindingBottomSheet.chipGroup.forEach { child ->
                    (child as Chip?)?.setOnCheckedChangeListener { _, _ ->
                        registerFilterChanged()
                    }
                }
//                (view?.findViewById(R.id.list_bottom_sheet_container) as ViewGroup?)?.removeView(view?.findViewById(R.id.list_bottom_sheet_container)!!)

                bottomSheetDialog.dismiss()
            }

        }

/*

        //Sort
        binding.btnSwapSort.setOnClickListener {
            listViewModel.setSort()
            val fragmentManager: FragmentManager? = activity?.supportFragmentManager
            val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
            val transactionAnimation = fragmentTransaction?.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
            transactionAnimation?.replace(R.id.list_frame_layout, ListMainFragment())?.commit()
        }
*/

        return binding.root
    }

    private fun registerFilterChanged() {
        val checkedChipIds = bindingBottomSheet.chipGroup.checkedChipIds

        val titlesChips = mutableListOf<CharSequence>()

        checkedChipIds.forEach { id ->
            titlesChips.add(bindingBottomSheet.chipGroup.findViewById<Chip>(id).text)
        }

        val text = if (titlesChips.isNotEmpty()) {
            titlesChips.joinToString(", ")
        } else {
            "No Choice"
        }

        Log.i("Lifecycle", text)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        //val recyclerViewState = itemRecyclerView.layoutManager?.onSaveInstanceState()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}