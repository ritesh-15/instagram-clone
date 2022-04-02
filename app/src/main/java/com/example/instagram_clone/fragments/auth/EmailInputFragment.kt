package com.example.instagram_clone.fragments.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram_clone.activities.auth.AuthActivity
import com.example.instagram_clone.databinding.FragmentEmailInputBinding


class EmailInputFragment : Fragment() {

    private lateinit var binding:FragmentEmailInputBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEmailInputBinding.inflate(layoutInflater,container,false)

        // log in button click
        binding.tvLogIn.setOnClickListener {
            val activity = activity as AuthActivity
            activity.supportFragmentManager.popBackStackImmediate()
        }

        // next button click
        binding.btnNext.setOnClickListener {
            val activity = activity as AuthActivity
            activity.replaceFragments(ConfirmationFragment())
        }

        return binding.root
    }


}