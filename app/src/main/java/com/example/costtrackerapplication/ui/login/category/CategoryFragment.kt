package com.example.costtrackerapplication.ui.login.category

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.AddFragmentBinding
import com.example.costtrackerapplication.databinding.CategoryFragmentBinding
import com.example.costtrackerapplication.ui.login.add.AddViewModel

class CategoryFragment : Fragment() {

    private lateinit var categoryViewModel: CategoryViewModel
    private var _binding: CategoryFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding
        _binding = CategoryFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Viewmodel
        categoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        binding.btnToAdd.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_addFragment)
        }
    }

}