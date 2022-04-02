package com.example.instagram_clone.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram_clone.activities.auth.AuthActivity
import com.example.instagram_clone.databinding.FragmentConfirmationBinding


class ConfirmationFragment : Fragment() {

    private lateinit var binding:FragmentConfirmationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentConfirmationBinding.inflate(layoutInflater,container,false)

        // login button click
        binding.tvSignIn.setOnClickListener {
            val activity = activity as AuthActivity
            activity.replaceFragments(LoginFragment())
        }

        // next button click
        binding.btnNext.setOnClickListener {
            val activity = activity as AuthActivity
            activity.replaceFragments(NameAndPasswordFragment())
        }

        return binding.root
    }


}