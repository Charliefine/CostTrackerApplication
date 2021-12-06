package com.example.costtrackerapplication.ui.login.home

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.costtrackerapplication.AddActivity
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.activities.ExpenseActivity
import com.example.costtrackerapplication.databinding.HomeMainFragmentBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class HomeMainFragment : Fragment() {

    private lateinit var homeMainViewModel: HomeMainViewModel
    private var _binding: HomeMainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentDate: LocalDateTime
    private lateinit var currentDateFormatted: String
    private lateinit var oldDate: LocalDateTime
    private lateinit var oldDateFormatted: String
    private lateinit var summaryExpense: String
    private lateinit var costLimit: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Binding
        _binding = HomeMainFragmentBinding.inflate(inflater, container, false)

        //Viewmodel
        homeMainViewModel = ViewModelProvider(this).get(HomeMainViewModel::class.java)

        //Default date
        setDefaultDate()
/*

        //Print summary
        val fragmentManager: FragmentManager? = activity?.supportFragmentManager
        var fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.home_main_summary_framelayout, HomeMainSummaryFragment())?.commit()

*/

/*

        //Set date
        binding.homeMainSummaryCardviewDate.setOnClickListener {
            val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

            val datePicker =
                MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Choose date range")
                    .setSelection(
                        Pair(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
                    .build()

            datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())

            datePicker.addOnPositiveButtonClickListener {
                oldDateFormatted = outputDateFormat.format(it.first)
                currentDateFormatted = outputDateFormat.format(it.second)
                val dateText = "$oldDateFormatted - $currentDateFormatted"
                binding.homeMainSummaryCardviewDate.text = dateText
                homeMainViewModel.setDateRange(oldDateFormatted, currentDateFormatted)

                //setNewDate()

                fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.home_main_summary_framelayout, HomeMainSummaryFragment())?.commit()

            }
        }
*/

        binding.btnToExpenseHomeMain.setOnClickListener {
            val intent = Intent(requireContext(), AddActivity::class.java)
            startActivity(intent)
        }

        binding.progressLine.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setTitle("Set new monthly limit")
            val viewInflated: View = LayoutInflater.from(context)
                .inflate(R.layout.home_main_dialog_text, view as ViewGroup?, false)
            val input = viewInflated.findViewById<View>(R.id.dialog_limit_input) as EditText
            builder.setView(viewInflated)
            builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()

                val newValue = input.text.toString()
                if(newValue == ""){}
                else{
                    setNewValue(newValue)
                }
            }
            builder.setNegativeButton(android.R.string.cancel
            ) { dialog, _ ->
                dialog.cancel() }
            builder.show()
        }

        return binding.root
    }

    private fun setNewDate() {
        homeMainViewModel.summaryExpense.observe(viewLifecycleOwner, Observer {
            binding.homeMainSummaryCardviewAmount.text = "$it PLN"
        })
    }

    private fun setSummary() {
        binding.homeMainSummaryCardviewAmount.text = "$summaryExpense PLN"
    }

    private fun setProgressBar() {
            if(costLimit == "0"){
                binding.progressLine.setmPercentage(100)
                binding.progressLine.setmValueText("$summaryExpense/$costLimit")
            }else{
                var percentProgress: Float = (summaryExpense.toFloat() / costLimit.toInt()) * 100
                binding.progressLine.setmPercentage(percentProgress.toInt())
                binding.progressLine.setmValueText("$summaryExpense/$costLimit")
            }
    }

    private fun setNewValue(newLimit: String) {
        homeMainViewModel.setNewLimit(newLimit)
        if(newLimit == "0"){
            binding.progressLine.setmPercentage(100)
            binding.progressLine.setmValueText("$summaryExpense/$newLimit")
        }else{
            var percentProgress: Float = (summaryExpense.toFloat() / newLimit.toInt()) * 100
            binding.progressLine.setmValueText("$summaryExpense/$newLimit")
            binding.progressLine.setmPercentage(percentProgress.toInt())
        }
/*        homeMainViewModel.costLimit.observe(viewLifecycleOwner, Observer {
            binding.progressLine.setmValueText("$summaryExpense/$it")
        })*/
    }

    private fun setDefaultDate() {
        currentDate = LocalDateTime.now()
        val formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        currentDateFormatted = currentDate.format(formatterDate)
        //oldDate = MaterialDatePicker.thisMonthInUtcMilliseconds() as LocalDateTime
        oldDate = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(MaterialDatePicker.thisMonthInUtcMilliseconds()),
            TimeZone.getDefault().toZoneId())
        oldDateFormatted = oldDate.format(formatterDate)

        val dateText = "$oldDateFormatted - $currentDateFormatted"
        binding.homeMainSummaryCardviewDate.text = dateText

        homeMainViewModel.setDateRange(oldDateFormatted, currentDateFormatted)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Title text
        homeMainViewModel.welcomeName.observe(viewLifecycleOwner, Observer {
            binding.homeMainTitle.text = "Welcome back,\n$it"
        })
        //Cost limit
        homeMainViewModel.costLimit.observe(viewLifecycleOwner, {
            costLimit = it
        })

        //Summary amount
        homeMainViewModel.summaryExpense.observe(viewLifecycleOwner, {
            summaryExpense = it
            setProgressBar()
            setSummary()
        })

        binding.homeMainSummaryCardview.setOnClickListener {
            val intent = Intent(requireContext(), ExpenseActivity::class.java)
            startActivity(intent)
        }

/*
        //Set progress bar
        setProgressBar()
        */
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val dates: ArrayList<String> = ArrayList()
        dates.add(oldDateFormatted)
        dates.add(currentDateFormatted)
        outState.putStringArrayList("Date", dates)
    }

}