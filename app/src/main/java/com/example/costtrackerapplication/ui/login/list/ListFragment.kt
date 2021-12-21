package com.example.costtrackerapplication.ui.login.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.ListFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.collections.ArrayList

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel
    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!
    private var filterList: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Binding
        _binding = ListFragmentBinding.inflate(inflater, container, false)

        //ViewModel
        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.Theme_Design_BottomSheetDialog)
        val bottomSheetView: View = LayoutInflater.from(requireContext()).inflate(R.layout.list_bottom_sheet_fragment, view?.findViewById(R.id.list_bottom_sheet_container))
        bottomSheetDialog.setContentView(bottomSheetView)

        //Filters
        binding.btnBottomSheetFilters.setOnClickListener {
            bottomSheetDialog.show()

            bottomSheetDialog.setOnCancelListener {
                registerFilterChanged(bottomSheetView)
                bottomSheetDialog.hide()

                // Recreate list
                val fragmentManager: FragmentManager? = activity?.supportFragmentManager
                val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
                val transactionAnimation = fragmentTransaction?.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
                transactionAnimation?.replace(R.id.list_frame_layout, ListMainFragment())?.commit()
            }

            bottomSheetView.findViewById<MaterialButton>(R.id.btn_bottom_sheet_apply).setOnClickListener {
                registerFilterChanged(bottomSheetView)
                bottomSheetDialog.hide()

                // Recreate list
                val fragmentManager: FragmentManager? = activity?.supportFragmentManager
                val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
                val transactionAnimation = fragmentTransaction?.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
                transactionAnimation?.replace(R.id.list_frame_layout, ListMainFragment())?.commit()

            }

        }

        //Print list
        val fragmentManager: FragmentManager? = activity?.supportFragmentManager
        val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        fragmentTransaction?.add(R.id.list_frame_layout, ListMainFragment())?.commit()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun registerFilterChanged(bottomSheetView: View) {
        filterList.clear()

        //Set filters
        val checkedChipIds = bottomSheetView.findViewById<ChipGroup>(R.id.chipGroup)
        checkedChipIds.checkedChipIds.forEach {
            val chip = bottomSheetView.findViewById<Chip>(it).text.toString()
            filterList.add(chip)
        }
        listViewModel.setFilters(filterList)
        if(bottomSheetView.findViewById<Chip>(R.id.chip_latest).isChecked) listViewModel.setSort("Latest")
        else listViewModel.setSort("Oldest")
    }

}