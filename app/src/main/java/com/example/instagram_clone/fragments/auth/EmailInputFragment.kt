package com.example.instagram_clone.fragments.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.instagram_clone.activities.auth.AuthActivity
import com.example.instagram_clone.api.auth.body.RegisterBody
import com.example.instagram_clone.api.auth.response.RegisterResponse
import com.example.instagram_clone.databinding.FragmentEmailInputBinding
import com.example.instagram_clone.network.factory.AuthViewModelFactory
import com.example.instagram_clone.network.models.AuthViewModel
import com.example.instagram_clone.repository.AuthRepository
import com.example.instagram_clone.utils.Constants
import com.example.instagram_clone.utils.GetResponseErrorBody
import com.example.instagram_clone.utils.Resource


class EmailInputFragment : Fragment() {

    private lateinit var binding:FragmentEmailInputBinding

    private lateinit var authViewModel:AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEmailInputBinding.inflate(layoutInflater,container,false)

        val repository = AuthRepository()
        val factory = AuthViewModelFactory(repository)
        authViewModel = ViewModelProvider(requireActivity(), factory)[AuthViewModel::class.java]

        // log in button click
        binding.tvLogIn.setOnClickListener {
            val activity = activity as AuthActivity
            activity.replaceFragments(LoginFragment())
        }


        // next button click
        binding.btnNext.setOnClickListener {
            handleNextBtnClick()
        }

        // register response observer
        authViewModel.register.observe(requireActivity()){
            response -> handleRegisterResponse(response)
        }

        return binding.root
    }

    private fun handleRegisterResponse(state: Resource<RegisterResponse>) {
        val activity = activity as AuthActivity

        when(state){

            is Resource.Loading -> {
                activity.showProgressDialog("Registering...")
            }

            is Resource.Success -> {
                activity.hideProgressDialog()
                if(state.data != null){

                    val email = state.data.email
                    val hash = state.data.hash

                    val bundle = Bundle()

                    bundle.putString(Constants.EMAIL,email)
                    bundle.putString(Constants.HASH,hash)

                    val fragment = ConfirmationFragment()
                    fragment.arguments = bundle

                    activity.replaceFragments(fragment)
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
        val email:String = binding.etEmailAddress.text.toString()

        if(email.isEmpty()){
            Toast.makeText(activity,"Email address is required!",Toast.LENGTH_SHORT).show()
            return
        }

        authViewModel.register(RegisterBody(email))
    }


}