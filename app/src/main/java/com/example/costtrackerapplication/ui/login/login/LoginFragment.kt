package com.example.costtrackerapplication.ui.login.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.costtrackerapplication.DrawerActivity
import com.example.costtrackerapplication.MainActivity
import com.example.costtrackerapplication.R
import com.example.costtrackerapplication.databinding.LoginFragmentBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    //GoogleAuth
    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding
        _binding = LoginFragmentBinding.inflate(inflater, container, false)

        binding.btnToLogin.setOnClickListener {
            val loginEmailInput: String? = binding.loginEmailInput.text.toString()
            val loginPasswordInput: String? = binding.loginPasswordInput.text.toString()
            when{
                TextUtils.isEmpty(loginEmailInput?.trim{it <= ' '}) && TextUtils.isEmpty(loginPasswordInput?.trim{it <= ' '}) -> {
                    binding.loginEmailLayout.error = "Email is empty"
                    binding.loginPasswordLayout.error = "Password is empty"
                }

                TextUtils.isEmpty(loginEmailInput?.trim{it <= ' '}) -> {
                    binding.loginEmailLayout.error = "Email is empty"
                    binding.loginPasswordLayout.error = null
                }

                TextUtils.isEmpty(loginPasswordInput?.trim{it <= ' '}) -> {
                    binding.loginPasswordLayout.error = "Password is empty"
                    binding.loginEmailLayout.error = null
                }

                else -> {
                    binding.loginEmailLayout.error = null
                    binding.loginPasswordLayout.error = null

                    loginViewModel.loginUserWithEmail(loginEmailInput, loginPasswordInput, context).observe(viewLifecycleOwner, Observer {
                        if(it == true){
                            val intent = Intent(activity, DrawerActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            MainActivity().finish()
                        }else{
                            binding.loginPasswordLayout.error = "Email or password is incorrect"
                        }
                    })
                }
            }
        }

        //Google button
        binding.btnLoginGoogle.setOnClickListener {
            googleSignIn()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Viewmodel
        loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.textToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.textForgotPasswd.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoveryPasswd2)
        }
    }

    //Log in if logged already
    override fun onStart() {
        checkLogIn()
        super.onStart()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                //MainActivity shouldn't be there at all
                loginViewModel.firebaseAuthWithGoogle(account.idToken!!, (activity as MainActivity))

                val intent = Intent(activity, DrawerActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                MainActivity().finish()
                Log.i("Lifecycle", "Google sign is success")
            } catch (e: ApiException) {
                Log.i("Lifecycle", "Google sign in failed")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkLogIn(){
        loginViewModel.isLogged().observe(viewLifecycleOwner, Observer {
            if(it == true){
                val intent = Intent(activity, DrawerActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                MainActivity().finish()
            }
        })
    }

    //Google
    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("862825264688-h6isc17im6geg49v7kh06gm8mdek4ocg.apps.googleusercontent.com")
            //.requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient((activity as MainActivity), gso)
    }

    private fun googleSignIn(){
        configureGoogleSignIn()
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
}