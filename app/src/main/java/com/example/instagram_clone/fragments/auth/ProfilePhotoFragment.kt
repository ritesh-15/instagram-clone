package com.example.instagram_clone.fragments.auth

import android.app.Activity
import android.app.Notification
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram_clone.activities.MainActivity
import com.example.instagram_clone.databinding.FragmentProfilePhotoBinding
import com.example.instagram_clone.utils.PermissionManager


class ProfilePhotoFragment : Fragment() {

    private lateinit var binding:FragmentProfilePhotoBinding

    private var mFileUri:Uri? = null

    private val action = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if(result.resultCode == Activity.RESULT_OK){
            mFileUri = result.data?.data
            // TODO show selected image
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
        ): View {
            binding = FragmentProfilePhotoBinding.inflate(layoutInflater,container,false)

            // add photo button click
            binding.btnAddPhoto.setOnClickListener {
                PermissionManager.checkStoragePermission(requireActivity(), RESULT_CODE){
                    showImageChooser()
                }
            }

            // skip button click
            binding.tvSkip.setOnClickListener {
                val intent = Intent(activity,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                activity?.finish()
            }

            return binding.root
        }

    private fun showImageChooser() {
       val gallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
       action.launch(gallery)
    }

    companion object{
        const val RESULT_CODE = 200
    }
}