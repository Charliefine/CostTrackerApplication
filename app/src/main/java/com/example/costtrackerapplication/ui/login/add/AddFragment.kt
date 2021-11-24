package com.example.costtrackerapplication.ui.login.add

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.AddFragmentBinding
import com.example.costtrackerapplication.databinding.RegisterFragmentBinding
import com.example.costtrackerapplication.ui.login.register.RegisterViewModel

class AddFragment : Fragment() {
    private lateinit var addViewModel: AddViewModel
    private var _binding: AddFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding
        _binding = AddFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Viewmodel
        addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)

        binding.btnToCategory.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_categoryFragment)
        }
    }

}