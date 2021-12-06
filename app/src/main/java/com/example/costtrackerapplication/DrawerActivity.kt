package com.example.costtrackerapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.costtrackerapplication.databinding.ActivityDrawerBinding
import com.example.costtrackerapplication.ui.login.add.AddFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class DrawerActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDrawerBinding

    //Google auth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = ActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarDrawer.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.drawerNavView
        val navController = findNavController(R.id.nav_host_fragment_content_drawer)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_about
            ), drawerLayout
        )

        navView.menu[3].setOnMenuItemClickListener {
            logOut()
            true
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        onBackPressedDispatcher.addCallback(this) {
            drawerLayout.closeDrawer(navView, true)
            if(!drawerLayout.isDrawerVisible(navView)) {
                MaterialAlertDialogBuilder(this@DrawerActivity)
                    .setIcon(R.drawable.ic_round_exit_to_app_24)
                    .setTitle("Leaving application")
                    .setMessage("Are you sure you want to leave application?")
                    .setPositiveButton("Yes") { _, _ ->
                        finish()
                    }
                    .setNegativeButton("No") { _, _ ->
                    }
                    .show()
            }
        }

        binding.appBarDrawer.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_drawer_bar_add -> {
                    val intent = Intent(this, AddActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_drawer_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun logOut(){
        //Google logout
        configureGoogleSignIn()
        mGoogleSignInClient.signOut()

        //Firebase instance logout
        FirebaseAuth.getInstance().signOut()

        startActivity(Intent(this@DrawerActivity, MainActivity::class.java))
        finish()
    }

    //Google SignInClient initializer
    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //.requestIdToken(getString(R.string.default_web_client_id))
            .requestIdToken("862825264688-h6isc17im6geg49v7kh06gm8mdek4ocg.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

}