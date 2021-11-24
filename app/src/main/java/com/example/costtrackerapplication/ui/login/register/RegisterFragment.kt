package com.example.costtrackerapplication.ui.login.register

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.costtrackerapplication.MainActivity
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.RegisterFragmentBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RegisterFragment : Fragment() {

    private lateinit var registerViewmodel: RegisterViewModel
    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)

        //Viewmodel
        registerViewmodel =
            ViewModelProvider(this).get(RegisterViewModel::class.java)

        binding.btnToRegisterAccount.setOnClickListener {
            val registerUsernameInput: String? = binding.registerUsernameInput.text.toString()
            val registerEmailInput: String? = binding.registerEmailInput.text.toString()
            val registerPasswordInput: String? = binding.registerPasswordInput.text.toString()
            val registerRePasswordInput: String? = binding.registerRePasswordInput.text.toString()
            //val isAnyEmpty: Boolean = checkIsEmpty()

            //TODO Make every combination correct for validation
            when{
/*                (checkIfAnyEmpty(registerUsernameInput, registerEmailInput, registerPasswordInput, registerRePasswordInput) == true) -> {
                    setErrors(registerUsernameInput, registerEmailInput, registerPasswordInput, registerRePasswordInput)
                }*/
                (TextUtils.isEmpty(registerUsernameInput?.trim{it <= ' '})
                        || TextUtils.isEmpty(registerEmailInput?.trim{it <= ' '})
                        || TextUtils.isEmpty(registerPasswordInput?.trim{it <= ' '})
                        || TextUtils.isEmpty(registerRePasswordInput?.trim{it <= ' '})) -> {
                        setErrors(registerUsernameInput, registerEmailInput, registerPasswordInput, registerRePasswordInput)
                }
                else -> {
                    //if(TextUtils.isEmpty(registerPasswordInput?.trim{it <= ' '}) != TextUtils.isEmpty(registerRePasswordInput?.trim{it <= ' '})){
                    if(checkIfEqual(registerPasswordInput, registerRePasswordInput)){
                        binding.registerPasswordLayout.error = "Passwords are not equal"
                        binding.registerRePasswordLayout.error = "Passwords are not equal"
                    }else{
                        binding.registerPasswordLayout.error = null
                        binding.registerRePasswordLayout.error = null

                        registerViewmodel.registerUser(
                            registerUsernameInput,
                            registerEmailInput,
                            registerPasswordInput
                        ).observe(viewLifecycleOwner, Observer {
                            if (it == true) {
                                Snackbar.make(requireView(), "Successfully created account $registerUsernameInput", Snackbar.LENGTH_SHORT)
                                    .show()
                                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                            } else {
                                binding.registerTitle.text = "EROROROOR"
                            }
                        })
                    }
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Actionbar back button
        val actionBar: Toolbar? = _binding?.toolbarRegisterFragment
        (activity as MainActivity?)!!.setSupportActionBar(actionBar)
        actionBar?.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun checkIfAnyEmpty(
        registerUsernameInput: String?,
        registerEmailInput: String?,
        registerPasswordInput: String?,
        registerRePasswordInput: String?): Any {
        return TextUtils.isEmpty(registerUsernameInput?.trim{it <= ' '}) || TextUtils.isEmpty(registerEmailInput?.trim{it <= ' '}) || TextUtils.isEmpty(registerPasswordInput?.trim{it <= ' '}) || TextUtils.isEmpty(registerRePasswordInput?.trim{it <= ' '})
    }

    private fun setErrors(
        registerUsernameInput: String?,
        registerEmailInput: String?,
        registerPasswordInput: String?,
        registerRePasswordInput: String?
    ) {
        if(TextUtils.isEmpty(registerUsernameInput?.trim{it <= ' '})) binding.registerUsernameLayout.error = "Username is empty"
        else binding.registerUsernameLayout.error = null
        if(TextUtils.isEmpty(registerEmailInput?.trim{it <= ' '})) binding.registerEmailLayout.error = "Email is empty"
        else binding.registerEmailLayout.error = null
        if(TextUtils.isEmpty(registerPasswordInput?.trim{it <= ' '})) binding.registerPasswordLayout.error = "Password is empty"
        else binding.registerPasswordLayout.error = null
        if(TextUtils.isEmpty(registerRePasswordInput?.trim{it <= ' '})) binding.registerRePasswordLayout.error = "Password is empty"
        else binding.registerRePasswordLayout.error = null
    }

    private fun checkIfEqual(registerPasswordInput: String?, registerRePasswordInput: String?): Boolean {
        return (TextUtils.isEmpty(registerPasswordInput?.trim{it <= ' '}) == TextUtils.isEmpty(registerRePasswordInput?.trim{it <= ' '}))
    }
}