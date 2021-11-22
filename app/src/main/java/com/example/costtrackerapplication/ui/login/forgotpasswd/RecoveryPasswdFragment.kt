package com.example.costtrackerapplication.ui.login.forgotpasswd

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.costtrackerapplication.MainActivity
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.RecoveryPasswdFragmentBinding
import com.google.android.material.snackbar.Snackbar

class RecoveryPasswdFragment : Fragment() {

    private lateinit var recoveryPasswdViewModel: RecoveryPasswdViewModel
    private var _binding: RecoveryPasswdFragmentBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding
        _binding = RecoveryPasswdFragmentBinding.inflate(inflater, container, false)

        binding.btnToRecoverPasswd.setOnClickListener {
            val recoveryPasswdEmailInput: String? = binding.recoveryPasswdEmailInput.text.toString().trim{it <= ' '}
            when{
                TextUtils.isEmpty(recoveryPasswdEmailInput) -> {
                    binding.recoveryPasswdEmailLayout.error = "Email is empty"
                }
                else -> {
                binding.recoveryPasswdEmailLayout.error = null
                recoveryPasswdViewModel.sendRecoveryPassword(
                    recoveryPasswdEmailInput
                ).observe(viewLifecycleOwner, Observer {
                    if (it == true) {
                        binding.recoveryPasswdIsSent.setTextColor(R.color.green)
                        binding.recoveryPasswdIsSent.text = "The message has been sent to provided email to reset password."
                    } else {
                        binding.recoveryPasswdIsSent.setTextColor(R.color.red)
                        binding.recoveryPasswdIsSent.text = "Provided email is incorrect or account does not exist."
                    }
                })
                }
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Viewmodel
        recoveryPasswdViewModel =
            ViewModelProvider(this).get(RecoveryPasswdViewModel::class.java)

        //Actionbar back button
        val actionBar: Toolbar? = _binding?.toolbarRecoveryPasswdFragment
        (activity as MainActivity?)!!.setSupportActionBar(actionBar)
        actionBar?.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_recoveryPasswd_to_loginFragment2)
        }

    }

}