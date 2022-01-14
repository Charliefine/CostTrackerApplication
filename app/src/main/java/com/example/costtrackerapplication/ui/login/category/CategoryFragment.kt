package com.example.costtrackerapplication.ui.login.category

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.costtrackerapplication.databinding.CategoryFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.core.os.bundleOf
import com.example.costtrackerapplication.model.CategoryAdapter


class CategoryFragment : BottomSheetDialogFragment() {

    private lateinit var categoryViewModel: CategoryViewModel
    private var _binding: CategoryFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Binding
        _binding = CategoryFragmentBinding.inflate(inflater, container, false)

        //Viewmodel
        categoryViewModel =
            ViewModelProvider(this).get(CategoryViewModel::class.java)

        categoryViewModel.categories.observe(viewLifecycleOwner, {
            var categoriesList = it.first
            var iconsList = it.second
            val listAdapter = CategoryAdapter(requireActivity(), categoriesList, iconsList)
            binding.categoryList.adapter = listAdapter


            //TODO Probably out, doesn't work 100% correct
            //Search Filter
            binding.searchViewCategoryList.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    listAdapter.filter.filter(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    listAdapter.filter.filter(newText)
                    return false
                }
            })

        binding.categoryList.setOnItemClickListener { _, _, position, _ ->
            val categoryName: String = listAdapter.getItem(position).toString()

            requireActivity().supportFragmentManager.setFragmentResult("requestKeyCategory", bundleOf("bundleKey" to categoryName))

            this.dialog?.dismiss()
        }
        })

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
        }
        return dialog
    }
}
