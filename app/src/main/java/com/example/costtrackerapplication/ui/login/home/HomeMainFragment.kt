package com.example.costtrackerapplication.ui.login.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.costtrackerapplication.activities.AddActivity
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.activities.ExpenseActivity
import com.example.costtrackerapplication.databinding.HomeMainFragmentBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
    private var costLimit: String = "0"

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Title text
        homeMainViewModel.welcomeName.observe(viewLifecycleOwner, {
            binding.homeMainTitle.text = "Welcome back,\n$it"
        })

        //Profile photo
        setProfilePhoto()

        //Icons in CardViews
        setIcons()


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

        binding.btnToExpenseHomeMain.setOnClickListener {
            val intent = Intent(requireContext(), AddActivity::class.java)
            startActivity(intent)
        }

        binding.homeMainSummaryCardview2.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder.setTitle("Set new monthly limit")
            val viewInflated: View = LayoutInflater.from(context)
                .inflate(R.layout.home_main_dialog_text, view as ViewGroup?, false)
            val input = viewInflated.findViewById<View>(R.id.dialog_limit_input) as EditText
            builder.setView(viewInflated)
            builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()

                val newValue = input.text.toString()
                if(newValue !== ""){
                    setNewValue(newValue)
                }
            }
            builder.setNegativeButton(android.R.string.cancel
            ) { dialog, _ ->
                dialog.cancel() }
            builder.show()
        }
    }

    private fun setIcons() {
        binding.homeMainSummaryCardviewImage.setImageResource(R.drawable.ic_outline_info_24)
        binding.homeMainSummaryCardview2Image.setImageResource(R.drawable.ic_outline_block_24)
    }

    private fun setProfilePhoto() {
        homeMainViewModel.welcomeName.observe(viewLifecycleOwner, {
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.ic_round_person_96)
                .circleCrop()
                .into(binding.homeMainSummaryImage)
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val dates: ArrayList<String> = ArrayList()
        dates.add(oldDateFormatted)
        dates.add(currentDateFormatted)
        outState.putStringArrayList("Date", dates)
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

}