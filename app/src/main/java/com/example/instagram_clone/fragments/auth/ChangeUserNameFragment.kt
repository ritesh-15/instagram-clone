package com.example.instagram_clone.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.instagram_clone.activities.auth.AuthActivity
import com.example.instagram_clone.api.users.body.ChangeUsernameBody
import com.example.instagram_clone.api.users.response.ChangeUserNameResponse
import com.example.instagram_clone.databinding.FragmentChangeUserNameBinding
import com.example.instagram_clone.network.factory.UserViewModelFactory
import com.example.instagram_clone.network.models.UserViewModel
import com.example.instagram_clone.repository.UserRepository
import com.example.instagram_clone.utils.Constants
import com.example.instagram_clone.utils.GetResponseErrorBody
import com.example.instagram_clone.utils.Resource


class ChangeUserNameFragment : Fragment() {

    private lateinit var binding:FragmentChangeUserNameBinding
    private lateinit var userViewModel:UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentChangeUserNameBinding.inflate(layoutInflater,container,false)

        val repository = UserRepository()
        val factory = UserViewModelFactory(repository)
        userViewModel = ViewModelProvider(requireActivity(),factory)[UserViewModel::class.java]

        // next button click
        binding.btnNext.setOnClickListener {
            handleNextBtnClick()
        }

        // change user name
        userViewModel.changeUsername.observe(requireActivity()){
            response -> handleChangeUsernameResponse(response)
        }

        return binding.root
    }

    private fun handleChangeUsernameResponse(state: Resource<ChangeUserNameResponse>) {
        val activity = activity as AuthActivity

        when(state){

            is Resource.Loading -> {
                activity.showProgressDialog("Changing...")
            }

            is Resource.Success -> {
                activity.hideProgressDialog()
                if(state.data != null){
                    val bundle = Bundle()
                    bundle.putSerializable(Constants.USER,state.data.user)
                    val fragment = ProfilePhotoFragment()
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
        val username:String = binding.etUsername.text.toString()
        if(username.isEmpty()){
            Toast.makeText(activity,"Username is required!",Toast.LENGTH_SHORT).show()
            return
        }

        userViewModel.changeUsername(ChangeUsernameBody(username))
    }


}