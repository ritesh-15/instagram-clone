package com.example.instagram_clone.repository

import com.example.instagram_clone.api.ApiInstance
import com.example.instagram_clone.api.auth.body.LoginBody
import com.example.instagram_clone.api.auth.response.LoginResponse
import retrofit2.Response

class AuthRepository {

    suspend fun login(body:LoginBody):Response<LoginResponse>{
        return ApiInstance.authApi.login(body)
    }

}