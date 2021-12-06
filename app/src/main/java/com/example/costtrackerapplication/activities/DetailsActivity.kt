package com.example.costtrackerapplication.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.ActivityDetailsBinding
import com.example.costtrackerapplication.model.FirebaseDatabaseRepository
import com.example.costtrackerapplication.ui.login.details.DetailsShowFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*


class DetailsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDetailsBinding

    private lateinit var itemID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*
        //Navigation
        val navController = findNavController(R.id.nav_host_fragment_content_details)
        appBarConfiguration = AppBarConfiguration(navController.graph)
*/

        //Receive item ID
        val bundleFromAdapter: Bundle? = intent.extras
        bundleFromAdapter?.let {
            bundleFromAdapter.apply {
                itemID = getString("itemID").toString()
            }
        }

        //Navigation
        val b = Bundle()
        b.putString("itemID", itemID)
        val navController = findNavController(R.id.nav_host_fragment_content_details)
        navController.setGraph(R.navigation.nav_graph_details, b)
        Log.i("Lifecycle", navController.currentDestination?.displayName.toString())

        setSupportActionBar(binding.detailsToolbar)

        settingStatusBarTransparent()
        binding.detailsToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        //Send item ID to fragment
        DetailsShowFragment().onFragmentInteraction(itemID)

        binding.detailsToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_details_bar_delete -> {
                    MaterialAlertDialogBuilder(this,
                        R.style.ThemeOverlay_MaterialComponents_Dark)
                        .setTitle("Deleting expense")
                        .setMessage("Are you sure you want to delete expense?")
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.cancel()
                        }
                        .setPositiveButton("Yes") { _, _ ->
                            FirebaseDatabaseRepository().deleteItem(itemID)
                            Toast.makeText(this, "Successfully deleted expense", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .show()
                }
                R.id.menu_details_bar_edit -> {
                    navController.navigate(R.id.action_detailsShowFragment_to_detailsEditFragment)
                }
            }
            true
        }

    }

    private fun settingStatusBarTransparent() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.statusBarColor = Color.TRANSPARENT
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details_bar, menu)

        return super.onCreateOptionsMenu(menu)
    }

}