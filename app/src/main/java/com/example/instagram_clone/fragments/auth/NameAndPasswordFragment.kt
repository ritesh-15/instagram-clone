package com.example.instagram_clone.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram_clone.activities.auth.AuthActivity
import com.example.instagram_clone.databinding.FragmentNameAndPasswordBinding


class NameAndPasswordFragment : Fragment() {

    private lateinit var binding:FragmentNameAndPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNameAndPasswordBinding.inflate(layoutInflater,container,false)


        // continue button click
        binding.btnNext.setOnClickListener {
            val activity = activity as AuthActivity
            activity.replaceFragments(NewUserWelcomeFragment())
        }

        return binding.root
    }


}