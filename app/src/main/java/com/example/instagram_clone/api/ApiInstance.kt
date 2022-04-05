package com.example.instagram_clone.api
import com.example.instagram_clone.api.auth.AuthInterface
import com.example.instagram_clone.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiInstance {

    private val retrofit:Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApi:AuthInterface by lazy {
        retrofit.create(AuthInterface::class.java)
    }

}