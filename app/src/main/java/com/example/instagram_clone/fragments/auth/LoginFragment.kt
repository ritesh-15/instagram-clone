package com.example.instagram_clone.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.instagram_clone.activities.auth.AuthActivity
import com.example.instagram_clone.api.auth.body.LoginBody
import com.example.instagram_clone.api.auth.response.LoginResponse
import com.example.instagram_clone.databinding.FragmentLoginBinding
import com.example.instagram_clone.network.factory.AuthViewModelFactory
import com.example.instagram_clone.network.models.AuthViewModel
import com.example.instagram_clone.repository.AuthRepository
import com.example.instagram_clone.utils.Resource


class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding

    private lateinit var authViewModel:AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        val repository = AuthRepository()
        val factory = AuthViewModelFactory(repository)
        authViewModel = ViewModelProvider(requireActivity(),factory)[AuthViewModel::class.java]

        // sign in button click
        binding.tvSignIn.setOnClickListener {
           val activity = activity as AuthActivity
            activity.replaceFragments(EmailInputFragment())
        }

        // login button click
        binding.btnLogin.setOnClickListener {
            handleLoginBtnClick()
        }

        // login call observer
        authViewModel.login.observe(requireActivity()){
            response -> handleLoginApiResponse(response)
        }

        return binding.root
    }

    private fun handleLoginApiResponse(state: Resource<LoginResponse>?) {
        when(state){
            is Resource.Loading -> {
                // TODO
            }

            is Resource.Error -> {
                // TODO
            }

            is Resource.Success -> {
                // TODO
            }
        }
    }

    private fun handleLoginBtnClick() {
        val email:String = binding.etEmailAddress.text.toString()
        val password:String = binding.etPassword.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(activity,"Email address and password is required!",Toast.LENGTH_SHORT).show()
            return
        }

        authViewModel.login(LoginBody(email, password))
    }


}