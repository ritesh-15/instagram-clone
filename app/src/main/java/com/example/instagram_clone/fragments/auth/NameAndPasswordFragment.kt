package com.example.instagram_clone.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.instagram_clone.activities.auth.AuthActivity
import com.example.instagram_clone.api.users.body.ActivateBody
import com.example.instagram_clone.api.users.response.ActivateResponse
import com.example.instagram_clone.databinding.FragmentNameAndPasswordBinding
import com.example.instagram_clone.network.factory.UserViewModelFactory
import com.example.instagram_clone.network.models.UserViewModel
import com.example.instagram_clone.repository.UserRepository
import com.example.instagram_clone.utils.Constants
import com.example.instagram_clone.utils.GetResponseErrorBody
import com.example.instagram_clone.utils.Resource


class NameAndPasswordFragment : Fragment() {

    private lateinit var binding:FragmentNameAndPasswordBinding
    private lateinit var userViewModel:UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNameAndPasswordBinding.inflate(layoutInflater,container,false)

        val repository = UserRepository()
        val factory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(requireActivity(),factory)[UserViewModel::class.java]

        // continue button click
        binding.btnNext.setOnClickListener {
            handleContinueBtnClick()
        }

        // activate response
        userViewModel.activate.observe(requireActivity()){
            response -> handleActivateResponse(response)
        }

        return binding.root
    }

    private fun handleActivateResponse(state: Resource<ActivateResponse>) {
        val activity = activity as AuthActivity
        when(state){
            is Resource.Loading -> {
                activity.showProgressDialog("Activating...")
            }

            is Resource.Success -> {
                activity.hideProgressDialog()
                if(state.data != null){
                    val bundle = Bundle()
                    bundle.putSerializable(Constants.USER,state.data.user)

                    val fragment = NewUserWelcomeFragment();
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

    private fun handleContinueBtnClick() {
        val name:String = binding.etFullName.text.toString()
        val password:String = binding.etPassword.text.toString()

        if(name.isEmpty() || password.isEmpty()){
            Toast.makeText(activity,"Name and password is required!",Toast.LENGTH_SHORT).show()
            return
        }

        userViewModel.activate(ActivateBody(name, password))
    }


}