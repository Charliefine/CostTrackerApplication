package com.example.costtrackerapplication.activities

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.costtrackerapplication.DrawerActivity
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.ActivityAddBinding
import com.example.costtrackerapplication.databinding.ActivityExpenseBinding
import com.example.costtrackerapplication.ui.login.expense.ExpenseSummaryFragment
import com.example.costtrackerapplication.ui.login.home.HomeMainFragment

class ExpenseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpenseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = ActivityExpenseBinding.inflate(layoutInflater)
        binding.root.isNestedScrollingEnabled = true
        setContentView(binding.root)

        val fragmentManager: FragmentManager = this.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.activity_expense_frame_layout, ExpenseSummaryFragment(),"ExpensePage").commit()
        supportActionBar?.title = "Summary"


        setSupportActionBar(binding.expenseToolbar)
        //binding.addAppbar.background = ColorDrawable(Color.parseColor("#00000000"))
        binding.expenseAppbar.setBackgroundColor(Color.TRANSPARENT)
        binding.expenseToolbar.setBackgroundColor(Color.TRANSPARENT)
        settingStatusBarTransparent()
//        actionBar?.elevation = 0F
        binding.expenseToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun settingStatusBarTransparent() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            val w: Window = window // in Activity's onCreate() for instance
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}