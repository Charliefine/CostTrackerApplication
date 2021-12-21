package com.example.costtrackerapplication.ui.login.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.LoginFragmentBinding
import com.example.costtrackerapplication.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Binding
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ViewModel
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        profileViewModel.getProfileDetails.observe(viewLifecycleOwner, {
            binding.profileUsername.text = it.username.toString()
            binding.profileEmail.text = it.email.toString()
            binding.profileRegisterDate.text = it.addedDate.toString()
            Glide.with(this)
                .load(it.photoURL.toString())
                .placeholder(R.drawable.ic_round_person_96)
                .circleCrop()
                .into(binding.profileImage)
        })
    }


}