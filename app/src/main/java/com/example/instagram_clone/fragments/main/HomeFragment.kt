package com.example.instagram_clone.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagram_clone.R
import com.example.instagram_clone.activities.MainActivity
import com.example.instagram_clone.adapters.PostAdapter
import com.example.instagram_clone.adapters.StoryAdapter
import com.example.instagram_clone.data.PostsData
import com.example.instagram_clone.databinding.FragmentHomeBinding
import com.example.instagram_clone.fragments.newPost.CreatePostDialogFragment
import com.example.instagram_clone.fragments.newPost.GetPostImageFragment
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

        // top bar on click
        binding.toolbarHomeFragment.setOnMenuItemClickListener{
            item ->

            when(item.itemId){

                R.id.menu_item_home_create_post -> {
                    val activity = activity as MainActivity
//                    activity.replaceFragments(GetPostImageFragment(),true)

                    val transaction = activity.supportFragmentManager.beginTransaction()
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    transaction
                        .add(android.R.id.content, CreatePostDialogFragment())
                        .addToBackStack(null)
                        .commit()
                }

            }

            true
        }

        return binding.root
    }


}