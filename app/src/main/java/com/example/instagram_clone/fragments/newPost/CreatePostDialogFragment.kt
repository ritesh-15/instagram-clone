package com.example.instagram_clone.fragments.newPost

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.instagram_clone.R
import com.example.instagram_clone.activities.MainActivity
import com.example.instagram_clone.databinding.FragmentCreatePostDialogBinding


class CreatePostDialogFragment : DialogFragment() {

    private lateinit var binding:FragmentCreatePostDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreatePostDialogBinding.inflate(layoutInflater,container,false)

        replaceFragments(GetPostImageFragment(), true)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    fun replaceFragments(fragment: Fragment,addToBackStack:Boolean = false){
        childFragmentManager.beginTransaction().apply {
            replace(binding.flCreatePostDialog.id,fragment)
            if(addToBackStack){
                addToBackStack(null)
            }
            commit()
        }
    }

}