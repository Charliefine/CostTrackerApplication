package com.example.costtrackerapplication.ui.login.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.costtrackerapplication.DrawerActivity
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.HomeFragmentBinding
import com.example.costtrackerapplication.ui.login.add.AddFragment
import com.example.costtrackerapplication.ui.login.list.ListFragment
import com.example.costtrackerapplication.ui.login.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    //private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

/*        val navHostFragment = DrawerActivity().supportFragmentManager.findFragmentById(R.id.homeFragment)
        val navController = navHostFragment?.findNavController()

        binding.bottomNavigationView.setupWithNavController(navController!!)
*//*
        val bottomNavigationView = DrawerActivity().findViewById<BottomNavigationView>(R.id.fragmentContainerView)
        val navController = bottomNavigationView?.findNavController()

        bottomNavigationView?.setupWithNavController(navController!!)*/



        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Viewmodel
        //bottomNavigationView = view?.findViewById(R.id.fragmentContainerView)!!
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val fragmentManager: FragmentManager? = activity?.supportFragmentManager
        val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        fragmentTransaction?.add(R.id.home_frame_layout, ListFragment())?.commit()
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigationView?.setOnNavigationItemSelectedListener {
            //var selectedFragment: Fragment? = null
            val fragmentManager: FragmentManager? = activity?.supportFragmentManager
            val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()

            when(it.itemId){
                R.id.bottom_nav_home -> {
                    Log.i("Lifecycle", "Clicked HOME")
                    //selectedFragment = ListFragment()
                    fragmentTransaction?.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
                    fragmentTransaction?.replace(R.id.home_frame_layout, ListFragment())?.commit()
                    true
                }
                R.id.bottom_nav_list -> {
                    Log.i("Lifecycle", "Clicked LIST")
                    //selectedFragment = ListFragment()
                    fragmentTransaction?.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
                    fragmentTransaction?.replace(R.id.home_frame_layout, ListFragment())?.commit()
                    true
                }
                R.id.bottom_nav_profile -> {
                    Log.i("Lifecycle", "Clicked PROFILE")
                    fragmentTransaction?.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)
                    fragmentTransaction?.replace(R.id.home_frame_layout, ProfileFragment())?.commit()
                    //selectedFragment = ProfileFragment()
                    true
                }
                else -> false
            }
        }
    }

}