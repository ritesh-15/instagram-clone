package com.example.instagram_clone.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram_clone.R
import com.example.instagram_clone.adapters.ViewPagerProfileTabsAdapter
import com.example.instagram_clone.data.PostsData
import com.example.instagram_clone.databinding.FragmentProfileBinding
import com.example.instagram_clone.databinding.FragmentProfilePhotoBinding
import com.google.android.material.tabs.TabLayoutMediator


class ProfileFragment : Fragment() {

    private lateinit var binding:FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater,container,false)

        val adapter = ViewPagerProfileTabsAdapter(activity?.supportFragmentManager!!, activity?.lifecycle!!)

        binding.vpPostTabs.adapter = adapter

        TabLayoutMediator(binding.tlProfile, binding.vpPostTabs){
            tab, position ->

            when(position){

                0 -> tab.setIcon(R.drawable.ic_profile_grid)

                1 -> tab.setIcon(R.drawable.ic_profile_user_tag)

            }

        }.attach()

        return binding.root
    }


}