package com.example.costtrackerapplication

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.costtrackerapplication.databinding.ActivityMainBinding
import com.example.costtrackerapplication.ui.login.login.LoginFragment
import com.example.costtrackerapplication.ui.login.register.RegisterFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        //Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Navigation
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
/*
        //Leaving app by clicking back button
        onBackPressedDispatcher.addCallback(this@MainActivity) {
                val dialogExit: Dialog = MaterialAlertDialogBuilder(this@MainActivity)
                    .setIcon(R.drawable.ic_round_exit_to_app_24)
                    .setTitle("Leaving application")
                    .setMessage("Are you sure to leave application?")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener() { dialog, which ->
                        finish()
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener() { dialog, which ->
                    })
                    .show()
        }*/

    }
/*

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}