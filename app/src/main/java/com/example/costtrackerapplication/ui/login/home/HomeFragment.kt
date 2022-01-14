package com.example.costtrackerapplication.ui.login.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.costtrackerapplication.activities.DrawerActivity
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.HomeFragmentBinding
import com.example.costtrackerapplication.ui.login.list.ListFragment
import com.example.costtrackerapplication.ui.login.profile.ProfileFragment

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Binding
        _binding = HomeFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentManager: FragmentManager? = activity?.supportFragmentManager
        val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        fragmentTransaction?.add(R.id.home_frame_layout, HomeMainFragment(),"HomePage")?.commit()
        (activity as DrawerActivity?)!!.supportActionBar?.title = "Home"

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
            val transactionAnimation = fragmentTransaction?.setCustomAnimations(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim, R.anim.nav_default_pop_enter_anim, R.anim.nav_default_pop_exit_anim)

            when(it.itemId){
                R.id.bottom_nav_home -> {
                        transactionAnimation?.replace(R.id.home_frame_layout, HomeMainFragment(), "HomePage")?.commit()
                        (activity as DrawerActivity?)!!.supportActionBar?.title = "Dashboard"
                    true
                }
                R.id.bottom_nav_list -> {
                    transactionAnimation?.replace(R.id.home_frame_layout, ListFragment(), "ListPage")?.commit()
                    (activity as DrawerActivity?)!!.supportActionBar?.title = "List of all expenses"
                    true
                }
                R.id.bottom_nav_profile -> {
                    transactionAnimation?.replace(R.id.home_frame_layout, ProfileFragment(), "ProfilePage")?.commit()
                    (activity as DrawerActivity?)!!.supportActionBar?.title = "Profile"
                    true
                }
                else -> false
            }
        }
        binding.bottomNavigationView.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.bottom_nav_home -> {
                    true
                }
                R.id.bottom_nav_list -> {
                    true
                }
                R.id.bottom_nav_profile -> {
                    true
                }
                else -> false
            }
        }
    }
}