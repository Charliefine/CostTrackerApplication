package com.example.costtrackerapplication.ui.login.details

import android.os.Bundle
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.activities.DetailsActivity
import com.example.costtrackerapplication.databinding.DetailsEditFragmentBinding
import com.example.costtrackerapplication.model.Item
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*


class DetailsEditFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private var _binding: DetailsEditFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var addedDate: String
    private lateinit var itemID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Delete menu options
        setHasOptionsMenu(true)

        //Binding
        _binding = DetailsEditFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Listener to category BottomSheet
        requireActivity().supportFragmentManager.setFragmentResultListener("requestKeyCategory", this ) { _, bundle ->
            val resultReceived = bundle.getString("bundleKey")
            binding.detailsEditCategoryLayout.hint = resultReceived
        }

        val bundleFromActivity = this.arguments
        itemID = bundleFromActivity?.getString("itemID").toString()

        //Delete menu items
        (activity as DetailsActivity).supportActionBar?.title = "Edit expense"

        //ViewModel
        detailsViewModel = ViewModelProvider(this, MyDetailsViewModelFactory(itemID)).get(DetailsViewModel::class.java)

        detailsViewModel.itemDetails.observe(viewLifecycleOwner, Observer {
            binding.detailsEditTitleInput.text = SpannableStringBuilder(it.title.toString())
            binding.detailsEditCategoryLayout.hint = it.category.toString()
            binding.detailsEditDateLayout.hint = it.date.toString()
            binding.detailsEditDescriptionInput.text = SpannableStringBuilder(it.description.toString())
            binding.detailsEditAmountInput.text = SpannableStringBuilder(it.amount.toString())
            addedDate = it.addedDate.toString()
        })

        disableCatAndDateInput()

        binding.detailsEditCategoryInput.setOnClickListener {
            findNavController().navigate(R.id.action_detailsEditFragment_to_categoryFragment2)
        }

        //Set date
        binding.detailsEditDateInput.setOnClickListener {
            val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Choose date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())

            datePicker.addOnPositiveButtonClickListener {
                binding.detailsEditDateLayout.hint = outputDateFormat.format(it)
            }
        }

        binding.btnDetailsEditExpense.setOnClickListener {
            editExpense()
        }
    }

    private fun editExpense(){
        val editDetailsTitleInput: String = binding.detailsEditTitleInput.text.toString().trim{it <= ' '}
        val editDetailsAmountInput: String = binding.detailsEditAmountInput.text.toString().trim{it <= ' '}
        val editDetailsDescriptionInput: String = binding.detailsEditDescriptionInput.text.toString().trim{it <= ' '}
        val editDetailsCategory: String? = binding.detailsEditCategoryLayout.hint as String?
        val editDetailsDate: String? = binding.detailsEditDateLayout.hint as String?
        when{
            TextUtils.isEmpty(editDetailsTitleInput) && TextUtils.isEmpty(editDetailsAmountInput) -> {
                binding.detailsEditTitleLayout.error = "Title is empty"
                binding.detailsEditAmountLayout.error = "Amount is empty"
            }

            TextUtils.isEmpty(editDetailsTitleInput) -> {
                binding.detailsEditTitleLayout.error = "Title is empty"
                binding.detailsEditAmountLayout.error = null
            }

            TextUtils.isEmpty(editDetailsAmountInput) -> {
                binding.detailsEditAmountLayout.error = "Amount is empty"
                binding.detailsEditTitleLayout.error = null
            }

            else -> {
                binding.detailsEditTitleLayout.error = null
                binding.detailsEditAmountLayout.error = null

                val item = Item(itemID, editDetailsTitleInput, editDetailsAmountInput, editDetailsDate, addedDate, editDetailsCategory, editDetailsDescriptionInput)

                //TODO ZROBIĆ ZWROTKĘ CZY OK?

                //Edit method
                detailsViewModel.editExpense(item)
                findNavController().popBackStack()
                Toast.makeText(requireContext(), "Successfully saved changes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun disableCatAndDateInput() {
        binding.detailsEditCategoryInput.inputType = InputType.TYPE_NULL
        binding.detailsEditDateInput.inputType = InputType.TYPE_NULL
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_details_bar_edit).isVisible = false
        menu.findItem(R.id.menu_details_bar_delete).isVisible = false
        menu.findItem(R.id.menu_details_bar_save).isVisible = true
        menu.findItem(R.id.menu_details_bar_save).setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_details_bar_save -> {
                    editExpense()
                }
            }
            true
        }
        super.onPrepareOptionsMenu(menu)
    }
}