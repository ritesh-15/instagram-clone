package com.example.instagram_clone.fragments.newPost

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram_clone.R
import com.example.instagram_clone.activities.MainActivity
import com.example.instagram_clone.databinding.FragmentGetPostImageBinding
import com.example.instagram_clone.utils.Constants
import com.example.instagram_clone.utils.PermissionManager


class GetPostImageFragment : Fragment() {

    private lateinit var binding:FragmentGetPostImageBinding

    private var mSelectedFileUri:Uri? = null

    // gallery intent result
    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            mSelectedFileUri = result.data?.data
            if(mSelectedFileUri != null){
                val activity = parentFragment as CreatePostDialogFragment
                val bundle = Bundle()
                bundle.putString(Constants.SELECTED_FILE,mSelectedFileUri!!.toString())
                val fragment = CaptionNewPostFragment()
                fragment.arguments = bundle
                activity.replaceFragments(fragment,true)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentGetPostImageBinding.inflate(layoutInflater,container, false)

        binding.toolbarChooseImage.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        // choose image button on click
        binding.btnChooseImage.setOnClickListener {
            PermissionManager.checkStoragePermission(requireActivity(), READ_STORAGE_CODE){
                showImageChooser()
            }
        }

        return binding.root
    }

    private fun showImageChooser() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
        activityResult.launch(galleryIntent)
    }

    companion object{
        const val READ_STORAGE_CODE = 201
    }

}