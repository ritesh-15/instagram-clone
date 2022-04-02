package com.example.instagram_clone.activities.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.instagram_clone.databinding.ActivityAuthBinding
import com.example.instagram_clone.fragments.auth.LoginFragment

class AuthActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set up binding
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // handle replace fragments
        handleReplaceFragments()
    }

    // handle replace fragments
    private fun handleReplaceFragments(){
        replaceFragments(LoginFragment(),true)
    }

    // replace fragments
    fun replaceFragments(fragment:Fragment,addToBackStack:Boolean = false){
       supportFragmentManager.beginTransaction().apply {
           replace(binding.flAuthLayout.id,fragment)
           if(addToBackStack){
               addToBackStack(null)
           }
           commit()
       }
    }
}