package com.example.instagram_clone.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager {

    companion object{

        fun checkStoragePermission(context:Activity, PERMISSION_CODE:Int , callback:() -> Unit){
            if(ContextCompat.checkSelfPermission(context,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                callback()
            }else{
                ActivityCompat.requestPermissions(
                    context,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_CODE
                )
            }
        }

    }

}