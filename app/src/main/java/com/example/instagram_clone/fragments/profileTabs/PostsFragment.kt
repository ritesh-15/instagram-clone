package com.example.instagram_clone.fragments.profileTabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.instagram_clone.R
import com.example.instagram_clone.adapters.ProfilePostsAdapter
import com.example.instagram_clone.data.PostsData
import com.example.instagram_clone.databinding.FragmentPostsBinding

class PostsFragment : Fragment() {

    private lateinit var binding:FragmentPostsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentPostsBinding.inflate(layoutInflater,container,false)

        val posts =  PostsData.getPosts()

        val adapter = ProfilePostsAdapter(requireContext(), posts)

        binding.rvProfilePosts.adapter = adapter

        binding.rvProfilePosts.layoutManager = GridLayoutManager(context,3)

        adapter.setOnClickListener(object:ProfilePostsAdapter.OnClickListener{
            override fun onClick(position: Int) {
                TODO("Not yet implemented")
            }

        })

        return binding.root
    }

}