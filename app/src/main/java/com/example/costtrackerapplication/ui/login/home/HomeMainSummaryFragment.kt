package com.example.costtrackerapplication.ui.login.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.HomeMainFragmentBinding
import com.example.costtrackerapplication.databinding.HomeMainSummaryFragmentBinding

class HomeMainSummaryFragment : Fragment() {

    private lateinit var homeMainViewModel: HomeMainViewModel
    private var _binding: HomeMainSummaryFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding
        _binding = HomeMainSummaryFragmentBinding.inflate(inflater, container, false)

        //Viewmodel
        homeMainViewModel = ViewModelProvider(this).get(HomeMainViewModel::class.java)


        //Summary amount
        homeMainViewModel.summaryExpense.observe(viewLifecycleOwner, Observer {
            binding.homeMainSummaryAmount.text = "$it PLN"
            binding.homeMainSummarySpinKit.isVisible = false
        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

/*
        //Summary amount
        homeMainViewModel.summaryExpense.observe(viewLifecycleOwner, Observer {
            binding.homeMainSummaryAmount.text = "$it PLN"
            binding.homeMainSummarySpinKit.isVisible = false
        })
*/

    }
}