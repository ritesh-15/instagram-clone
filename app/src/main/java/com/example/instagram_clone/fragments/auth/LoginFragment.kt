package com.example.instagram_clone.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram_clone.activities.auth.AuthActivity
import com.example.instagram_clone.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View{
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        // sign in button click
        binding.tvSignIn.setOnClickListener {
           val activity = activity as AuthActivity
            activity.replaceFragments(EmailInputFragment())
        }

        return binding.root
    }


}