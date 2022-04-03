package com.example.instagram_clone.fragments.newPost

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import com.example.instagram_clone.R
import com.example.instagram_clone.databinding.FragmentCaptionNewPostBinding
import com.example.instagram_clone.utils.Constants


class CaptionNewPostFragment : Fragment() {

    private lateinit var binding:FragmentCaptionNewPostBinding

    private var mSelectedFileUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCaptionNewPostBinding.inflate(layoutInflater,container,false)

        // navigation button click
        binding.toolbarCaptionPost.setNavigationOnClickListener {
            parentFragment?.activity?.onBackPressed()
        }

        mSelectedFileUri = arguments?.getString(Constants.SELECTED_FILE)?.toUri()

        setUpUiData()

        return binding.root
    }

    private fun  setUpUiData(){
        if(mSelectedFileUri != null){
            binding.ivPostImage.setImageURI(mSelectedFileUri!!)
        }
    }

}