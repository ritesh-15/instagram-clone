package com.example.instagram_clone.fragments.auth

import android.os.Bundle
import android.os.TokenWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.instagram_clone.R
import com.example.instagram_clone.activities.auth.AuthActivity
import com.example.instagram_clone.api.auth.body.ResendOtpBody
import com.example.instagram_clone.api.auth.body.VerifyOtpBody
import com.example.instagram_clone.api.auth.response.ResendOtpResponse
import com.example.instagram_clone.api.auth.response.VerifyOtpResponse
import com.example.instagram_clone.databinding.FragmentConfirmationBinding
import com.example.instagram_clone.network.factory.AuthViewModelFactory
import com.example.instagram_clone.network.models.AuthViewModel
import com.example.instagram_clone.repository.AuthRepository
import com.example.instagram_clone.utils.Constants
import com.example.instagram_clone.utils.GetResponseErrorBody
import com.example.instagram_clone.utils.Resource
import com.example.instagram_clone.utils.TokenHandler


class ConfirmationFragment : Fragment() {

    private lateinit var binding:FragmentConfirmationBinding
    private var email:String? = null
    private var hash:String? = null
    private lateinit var authViewModel:AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentConfirmationBinding.inflate(layoutInflater,container,false)

        val repository = AuthRepository()
        val factory = AuthViewModelFactory(repository)
        authViewModel = ViewModelProvider(requireActivity(), factory)[AuthViewModel::class.java]

        email = arguments?.getString(Constants.EMAIL)
        hash = arguments?.getString(Constants.HASH)

        binding.tvEmailMessage.text = getString(R.string.confirmation_code_message,email)

        // login button click
        binding.tvSignIn.setOnClickListener {
            val activity = activity as AuthActivity
            activity.replaceFragments(LoginFragment())
        }

        // next button click
        binding.btnNext.setOnClickListener {
            handleNextBtnClick()
        }

        // resend otp
        binding.tvResendOtp.setOnClickListener {
            handleResendOtp()
        }

        // verify otp observer
        authViewModel.verifyOtp.observe(requireActivity()){
            response -> handleVerifyOtpResponse(response)
        }

        // resend otp observer
        authViewModel.resendOtp.observe(requireActivity()){
            response -> handleResendOtpResponse(response)
        }

        return binding.root
    }

    private fun handleResendOtpResponse(state: Resource<ResendOtpResponse>?) {
        val activity = activity as AuthActivity
        when(state){
            is Resource.Loading -> {
                activity.showProgressDialog("Resending...")
            }

            is Resource.Success -> {
                activity.hideProgressDialog()
                if(state.data != null){
                     email = state.data.email
                     hash = state.data.hash
                     Toast.makeText(activity,"One time password sent successfully!",Toast.LENGTH_SHORT).show()
                }

            }

            is Resource.Error -> {
                activity.hideProgressDialog()
                val errorBody = GetResponseErrorBody.get(state.errorBody!!)
                Toast.makeText(activity, errorBody.error.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleResendOtp() {
        if(email == null){
            Toast.makeText(activity,"Email not found!", Toast.LENGTH_SHORT).show()
            return
        }

        authViewModel.resendOtp(ResendOtpBody(email!!))

    }

    private fun handleVerifyOtpResponse(state: Resource<VerifyOtpResponse>) {
        val activity = activity as AuthActivity
        when(state){
            is Resource.Loading -> {
                activity.showProgressDialog("Verifying...")
            }

            is Resource.Success -> {
                activity.hideProgressDialog()
                if(state.data != null){
                    TokenHandler.saveTokens(activity,state.data.tokens)
                    activity.replaceFragments(NameAndPasswordFragment())
                }
            }

            is Resource.Error -> {
                activity.hideProgressDialog()
                val errorBody = GetResponseErrorBody.get(state.errorBody!!)
                Toast.makeText(activity, errorBody.error.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleNextBtnClick() {
        val otp:String = binding.etConfirmationCode.text.toString()

        if(otp.isEmpty()){
            Toast.makeText(activity,"One time password is required!",Toast.LENGTH_SHORT).show()
            return
        }

        authViewModel.verifyOtp(VerifyOtpBody(email!!,hash!!,otp.toInt()))
    }


}