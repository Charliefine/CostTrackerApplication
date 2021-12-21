package com.example.costtrackerapplication.ui.login.add

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.AddFragmentBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class AddFragment : Fragment() {
    private lateinit var addViewModel: AddViewModel
    private var _binding: AddFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentDate: LocalDateTime
    private lateinit var formattedDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Binding
        _binding = AddFragmentBinding.inflate(inflater, container, false)

        //Set menu item
        setHasOptionsMenu(true)

        //Set default date and category
        setCurrentDate()
        setCategoryAndDate()

        //Listeners to category BottomSheet
        requireActivity().supportFragmentManager.setFragmentResultListener("requestKeyCategory", this ) { _, bundle ->
            val resultReceived = bundle.getString("bundleKey")
            binding.addCategoryLayout.hint = resultReceived
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Viewmodel
        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)

        //Set date
        binding.addDateInput.setOnClickListener {
            val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Choose date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())

            datePicker.addOnPositiveButtonClickListener {
                binding.addDateLayout.hint = outputDateFormat.format(it)
            }
        }

        //Validation
        binding.btnAddExpense.setOnClickListener {
            addNewExpense()
        }

        binding.addCategoryInput.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_categoryFragment)
        }
    }

    private fun addNewExpense() {
            val addTitleInput: String = binding.addTitleInput.text.toString().trim{it <= ' '}
            val addAmountInput: String = binding.addAmountInput.text.toString().trim{it <= ' '}
            val addDescriptionInput: String = binding.addDescriptionInput.text.toString().trim{it <= ' '}
            val addCategory: String? = binding.addCategoryLayout.hint as String?
            val addDate: String? = binding.addDateLayout.hint as String?
            when{
                TextUtils.isEmpty(addTitleInput) && TextUtils.isEmpty(addAmountInput) -> {
                    binding.addTitleLayout.error = "Title is empty"
                    binding.addAmountLayout.error = "Amount is empty"
                }

                TextUtils.isEmpty(addTitleInput) -> {
                    binding.addTitleLayout.error = "Title is empty"
                    binding.addAmountLayout.error = null
                }

                TextUtils.isEmpty(addAmountInput) -> {
                    binding.addAmountLayout.error = "Amount is empty"
                    binding.addTitleLayout.error = null
                }

                else -> {
                    binding.addTitleLayout.error = null
                    binding.addAmountLayout.error = null

                    //Add method
                    addViewModel.addExpense(addTitleInput, addAmountInput, addDescriptionInput, addCategory, addDate, formattedDate).observe(viewLifecycleOwner, Observer {
                        if(it == true){
                            Snackbar.make(requireView(), "Successfully added new expense", Snackbar.LENGTH_LONG).show()
                            binding.addTitleInput.text?.clear()
                            binding.addAmountInput.text?.clear()
                            binding.addDescriptionInput.text?.clear()
                        }else{
                            Snackbar.make(requireView(), "Something went wrong during adding new expense", Snackbar.LENGTH_LONG).show()
                        }
                    })
                }
            }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menu_add_bar_save).setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_add_bar_save -> {
                    addNewExpense()
                    Log.i("Lifecycle", "Clicked menu")
                }
            }
            true
        }
        super.onPrepareOptionsMenu(menu)
    }

    private fun setCurrentDate() {
        currentDate = LocalDateTime.now()
        val formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        formattedDate = currentDate.format(formatterDate)

    }

    private fun setCategoryAndDate() {
        binding.addCategoryInput.inputType = InputType.TYPE_NULL
        binding.addCategoryLayout.hint = "Food"
        binding.addDateInput.inputType = InputType.TYPE_NULL
        binding.addDateLayout.hint = formattedDate
    }
}
