package com.example.instagram_clone.fragments.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.instagram_clone.activities.MainActivity
import com.example.instagram_clone.activities.auth.AuthActivity
import com.example.instagram_clone.api.auth.body.LoginBody
import com.example.instagram_clone.api.auth.response.LoginResponse
import com.example.instagram_clone.databinding.FragmentLoginBinding
import com.example.instagram_clone.models.ErrorResponse
import com.example.instagram_clone.network.factory.AuthViewModelFactory
import com.example.instagram_clone.network.models.AuthViewModel
import com.example.instagram_clone.repository.AuthRepository
import com.example.instagram_clone.utils.Constants
import com.example.instagram_clone.utils.GetResponseErrorBody
import com.example.instagram_clone.utils.Resource
import com.example.instagram_clone.utils.TokenHandler
import com.google.gson.Gson


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
        val activity = activity as AuthActivity
        when(state){
            is Resource.Loading -> {
                activity.showProgressDialog("Validating...")
            }

            is Resource.Error -> {
                activity.hideProgressDialog()
                val errorBody = GetResponseErrorBody.get(state.errorBody!!)
                Toast.makeText(activity,errorBody.error.message,Toast.LENGTH_SHORT).show()
            }

            is Resource.Success -> {
                activity.hideProgressDialog()
                if(state.data != null){
                    val user = state.data.user
                    TokenHandler.saveTokens(activity,state.data.tokens)

                    if(!user.isVerified){
                        val bundle = Bundle()
                        bundle.putString(Constants.EMAIL,user.email)
                        val fragment = VerifyEmailFragment()
                        fragment.arguments = bundle
                        activity.replaceFragments(fragment)
                    }else if(user.name.isEmpty()){
                        activity.replaceFragments(NameAndPasswordFragment())
                    }else{
                        val intent = Intent(activity,MainActivity::class.java)
                        intent.putExtra(Constants.USER,user)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }

                }
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