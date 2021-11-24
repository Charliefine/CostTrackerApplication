package com.example.costtrackerapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.costtrackerapplication.databinding.ActivityAddBinding
import com.example.costtrackerapplication.databinding.ActivityMainBinding
import com.example.costtrackerapplication.ui.login.add.AddFragment

class AddActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Navigation
        val navController = findNavController(R.id.nav_host_fragment_content_add)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setSupportActionBar(binding.toolbarAdd)
        binding.toolbarAdd?.setNavigationOnClickListener {
            onBackPressed()
        }
/*
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AddFragment.newInstance())
                .commitNow()
        }
        */
    }
}