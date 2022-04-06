package com.example.instagram_clone.fragments.auth

import android.os.Bundle
import android.provider.SyncStateContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram_clone.activities.auth.AuthActivity
import com.example.instagram_clone.databinding.FragmentNewUserWelcomeBinding
import com.example.instagram_clone.models.User
import com.example.instagram_clone.utils.Constants

class NewUserWelcomeFragment : Fragment() {

    private lateinit var binding:FragmentNewUserWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewUserWelcomeBinding.inflate(layoutInflater,container,false)

        val user = arguments?.getSerializable(Constants.USER) as User

        binding.tvUserName.text = user.name

        // next button click
        binding.btnNext.setOnClickListener {
            val activity = activity as AuthActivity
            activity.replaceFragments(ProfilePhotoFragment())
        }

        // change username button click
        binding.tvChangeUsername.setOnClickListener {
            val activity = activity as AuthActivity
            activity.replaceFragments(ChangeUserNameFragment())
        }

        return binding.root
    }
}