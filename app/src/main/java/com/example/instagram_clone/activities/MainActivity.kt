package com.example.instagram_clone.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.instagram_clone.R
import com.example.instagram_clone.databinding.ActivityMainBinding
import com.example.instagram_clone.fragments.main.HomeFragment
import com.example.instagram_clone.fragments.main.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragments(HomeFragment())

        // bottom navigation on click listener
        binding.bnMain.setOnItemSelectedListener{
            when(it.itemId){

                R.id.item_profile -> {
                    replaceFragments(ProfileFragment(),true)
                }

                R.id.item_like -> {
                    Toast.makeText(this,"Like clicked",Toast.LENGTH_SHORT).show()
                }

                else -> {
                    replaceFragments(HomeFragment())
                }

            }

            true
        }
    }

    // replace fragments
    fun replaceFragments(fragment: Fragment,addToBackStack:Boolean = false){
        supportFragmentManager.beginTransaction().apply {
            replace(binding.flMainActivity.id,fragment)
            if(addToBackStack){
                addToBackStack(null)
            }
            commit()
        }
    }
}