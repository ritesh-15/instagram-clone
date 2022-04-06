package com.example.instagram_clone.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.instagram_clone.R
import com.example.instagram_clone.activities.auth.AuthActivity
import com.example.instagram_clone.api.auth.body.ResendOtpBody
import com.example.instagram_clone.api.auth.response.ResendOtpResponse
import com.example.instagram_clone.databinding.FragmentVerifyEmailBinding
import com.example.instagram_clone.network.factory.AuthViewModelFactory
import com.example.instagram_clone.network.models.AuthViewModel
import com.example.instagram_clone.repository.AuthRepository
import com.example.instagram_clone.utils.Constants
import com.example.instagram_clone.utils.GetResponseErrorBody
import com.example.instagram_clone.utils.Resource


class VerifyEmailFragment : Fragment() {

    private lateinit var binding:FragmentVerifyEmailBinding
    private lateinit var authViewModel:AuthViewModel

    private var email:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVerifyEmailBinding.inflate(layoutInflater,container,false)

        authViewModel = ViewModelProvider(requireActivity(),
            AuthViewModelFactory(AuthRepository())
            )[AuthViewModel::class.java]

        email = arguments?.getString(Constants.EMAIL)

        binding.btnVerifyEmail.setOnClickListener {
            handleVerifyBtnClick()
        }

        authViewModel.resendOtp.observe(requireActivity()){
            response -> parseResendOtpResonse(response)
        }

        return binding.root
    }

    private fun parseResendOtpResonse(state: Resource<ResendOtpResponse>) {
        val activity = activity as AuthActivity
        when(state){

            is Resource.Loading ->{
                activity.showProgressDialog("Resending...")
            }

            is Resource.Success -> {
                activity.hideProgressDialog()
                if(state.data != null){
                    val bundle = Bundle()
                    bundle.putString(Constants.EMAIL,state.data.email)
                    bundle.putString(Constants.HASH,state.data.hash)
                    val fragment = ConfirmationFragment()
                    fragment.arguments = bundle
                    activity.replaceFragments(fragment)
                }
            }

            is Resource.Error -> {
                activity.hideProgressDialog()
                val errorBody = GetResponseErrorBody.get(state.errorBody!!)
                Toast.makeText(activity,errorBody.error.message,Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun handleVerifyBtnClick() {
        if(email == null){
            val activity = activity as AuthActivity
            activity.replaceFragments(LoginFragment())
        }else{
            authViewModel.resendOtp(ResendOtpBody(email!!))
        }
    }


}