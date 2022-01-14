package com.example.costtrackerapplication.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.ActivityExpenseBinding
import com.example.costtrackerapplication.ui.login.expense.ExpenseSummaryFragment
import com.example.costtrackerapplication.ui.login.expense.ExpenseViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import androidx.core.util.Pair

class ExpenseActivity : AppCompatActivity() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var binding: ActivityExpenseBinding

    private lateinit var currentDate: LocalDateTime
    private lateinit var currentDateFormatted: String
    private lateinit var oldDate: LocalDateTime
    private lateinit var oldDateFormatted: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = ActivityExpenseBinding.inflate(layoutInflater)
        binding.root.isNestedScrollingEnabled = true
        setContentView(binding.root)

        //ViewModel
        expenseViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)

        binding.expenseActivityDateInput.inputType = InputType.TYPE_NULL

        val fragmentManager: FragmentManager = this.supportFragmentManager
        var fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.activity_expense_frame_layout, ExpenseSummaryFragment(),"ExpensePage").commit()

        supportActionBar?.title = "Summary"

        //Default date
        setDefaultDate()

        //Set date
        binding.expenseActivityDateInput.setOnClickListener {
            val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

            val datePicker =
                MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("Choose date range")
                    .setSelection(
                        Pair(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
                    .build()

            datePicker.show(this.supportFragmentManager, datePicker.toString())

            datePicker.addOnPositiveButtonClickListener {
                oldDateFormatted = outputDateFormat.format(it.first)
                currentDateFormatted = outputDateFormat.format(it.second)
                val dateText = "$oldDateFormatted - $currentDateFormatted"
//                binding.expenseSummaryDate.text = dateText
                binding.expenseActivityDateLayout.hint = dateText

                expenseViewModel.setDateRange(oldDateFormatted, currentDateFormatted)

                fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.activity_expense_frame_layout, ExpenseSummaryFragment()).commit()
            }
        }

        //Reset category
        expenseViewModel.setCategory("")

        setSupportActionBar(binding.expenseToolbar)
        binding.expenseAppbar.setBackgroundColor(Color.TRANSPARENT)
        binding.expenseToolbar.setBackgroundColor(Color.TRANSPARENT)
        settingStatusBarTransparent()

        binding.expenseToolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun settingStatusBarTransparent() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun setDefaultDate() {
        currentDate = LocalDateTime.now()
        val formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        currentDateFormatted = currentDate.format(formatterDate)
        oldDate = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(MaterialDatePicker.thisMonthInUtcMilliseconds()),
            TimeZone.getDefault().toZoneId())
        oldDateFormatted = oldDate.format(formatterDate)

        val dateText = "$oldDateFormatted - $currentDateFormatted"
//        binding.expenseSummaryDate.text = dateText
        binding.expenseActivityDateLayout.hint = dateText
        expenseViewModel.setDateRange(oldDateFormatted, currentDateFormatted)
    }

}