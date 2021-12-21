package com.example.costtrackerapplication

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import com.example.costtrackerapplication.databinding.ActivityMainBinding
import com.example.costtrackerapplication.ui.login.login.LoginFragment
import com.example.costtrackerapplication.ui.login.register.RegisterFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.FirebaseApp
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_CostTrackerApplication_NoActionBar)

        FirebaseApp.initializeApp(this)

        //Settings for dark mode
        setApplicationTheme()

        //Settings for language
        setApplicationLanguage()

        //Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Navigation
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        //TODO Add later
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
        }
*/

    }

    private fun setApplicationLanguage() {
        val languageValues = resources.getStringArray(R.array.language_values)
        val prefLang: String? = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("Language", "english_lang")
        if (prefLang.equals(languageValues[0])) {
            val locale = Locale("en")
            Locale.setDefault(locale)
            val resources = baseContext.resources
            val conf = resources.configuration
            conf.setLocale(locale)
            resources.updateConfiguration(conf, resources.displayMetrics)
        }
        if (prefLang.equals(languageValues[1])) {
            val locale = Locale("pl")
            Locale.setDefault(locale)
            val resources = baseContext.resources
            val conf = resources.configuration
            conf.setLocale(locale)
            resources.updateConfiguration(conf, resources.displayMetrics)
        }
    }

    private fun setApplicationTheme() {
        val darkModeValues = resources.getStringArray(R.array.theme_values)
        val prefTheme: String? = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("Dark Mode", "MODE_NIGHT_FOLLOW_SYSTEM")
        if (prefTheme.equals(darkModeValues[0]))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        if (prefTheme.equals(darkModeValues[1]))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (prefTheme.equals(darkModeValues[2]))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}