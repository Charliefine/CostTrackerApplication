package com.example.costtrackerapplication

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.costtrackerapplication.databinding.ActivityAddBinding


class AddActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAddBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = ActivityAddBinding.inflate(layoutInflater)
        binding.root.isNestedScrollingEnabled = true
        setContentView(binding.root)

        //Navigation
        val navController = findNavController(R.id.nav_host_fragment_content_add)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setSupportActionBar(binding.toolbarAdd)
        //binding.addAppbar.background = ColorDrawable(Color.parseColor("#00000000"))
/*        binding.addAppbar.setBackgroundColor(Color.TRANSPARENT)
        binding.toolbarAdd.setBackgroundColor(Color.TRANSPARENT)*/
        settingStatusBarTransparent()
//        actionBar?.elevation = 0F
        binding.toolbarAdd.setNavigationOnClickListener {
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
}