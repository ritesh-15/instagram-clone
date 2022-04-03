package com.example.instagram_clone.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.instagram_clone.fragments.profileTabs.PostsFragment
import com.example.instagram_clone.fragments.profileTabs.PostsTagsFragment

class ViewPagerProfileTabsAdapter(
    fragment:FragmentManager,lifecycle:Lifecycle
): FragmentStateAdapter(
fragment,lifecycle
) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                PostsFragment()
            }
            1 ->{
                PostsTagsFragment()
            }
            else -> PostsFragment()
        }
    }

}