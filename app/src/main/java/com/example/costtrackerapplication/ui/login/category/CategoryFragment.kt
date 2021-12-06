package com.example.costtrackerapplication.ui.login.category

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.costtrackerapplication.databinding.CategoryFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.app.Activity

import android.util.DisplayMetrics

import com.google.android.material.bottomsheet.BottomSheetBehavior

import android.R
import android.content.res.Resources
import android.util.Log
import android.widget.BaseAdapter

import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.core.os.bundleOf
import com.example.costtrackerapplication.databinding.AddFragmentBinding
import com.example.costtrackerapplication.ui.login.add.AddFragment


class CategoryFragment : BottomSheetDialogFragment() {

    private lateinit var categoryViewModel: CategoryViewModel
    private var _binding: CategoryFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Binding
        _binding = CategoryFragmentBinding.inflate(inflater, container, false)

        //Viewmodel
        categoryViewModel =
            ViewModelProvider(this).get(CategoryViewModel::class.java)

        var itemsList: ArrayList<String>

        categoryViewModel.text.observe(viewLifecycleOwner, Observer {

            itemsList = it
            var adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), com.example.costtrackerapplication.R.layout.list_item, itemsList)
            binding.categoryList.adapter = adapter

            //Search Filter
            binding.searchViewCategoryList.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                        adapter.filter.filter(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }
            })
        binding.categoryList.setOnItemClickListener { _, _, position, _ ->
            val categoryName: String = adapter.getItem(position).toString()

            requireActivity().supportFragmentManager.setFragmentResult("requestKey", bundleOf("bundleKey" to categoryName))

            this.dialog?.dismiss()
        }
        })

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            //setupFullHeight(bottomSheetDialog)
        }
        return dialog
    }
    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.example.costtrackerapplication.R.id.add_category_bottom_sheet) as RelativeLayout
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
        behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

}
