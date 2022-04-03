package com.example.instagram_clone.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagram_clone.R
import com.example.instagram_clone.adapters.PostAdapter
import com.example.instagram_clone.adapters.StoryAdapter
import com.example.instagram_clone.data.PostsData
import com.example.instagram_clone.databinding.FragmentHomeBinding
import com.example.instagram_clone.models.Post

class HomeFragment : Fragment() {

    private lateinit var binding :FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)


        // set up dummy post data

        val posts = PostsData.getPosts()

        posts.add(Post(3,"rohit_13k",
            "https://images.unsplash.com/photo-1611416370495-50fac9e1b382?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHw0fHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=600&q=60",
            "Third post"))

        val adapter = PostAdapter(requireActivity(),posts)

        adapter.setOnClickListener(object: PostAdapter.OnClickListener{
            override fun onClick(position: Int) {
                Toast.makeText(activity,"Clicked",Toast.LENGTH_SHORT).show()
            }

        })

        binding.rvPosts.adapter = adapter

        binding.rvPosts.layoutManager = StaggeredGridLayoutManager(1,
            StaggeredGridLayoutManager.VERTICAL)


        val storyAdapter = StoryAdapter(requireContext(), posts)

        storyAdapter.setOnClickListener(object: StoryAdapter.OnClickListener{
            override fun onClick(position: Int) {
                Toast.makeText(activity,"Clicked",Toast.LENGTH_SHORT).show()
            }

        })

        binding.rvStoriesContainer.adapter = storyAdapter

        binding.rvStoriesContainer.layoutManager = StaggeredGridLayoutManager(1,
            StaggeredGridLayoutManager.HORIZONTAL)

        return binding.root
    }


}