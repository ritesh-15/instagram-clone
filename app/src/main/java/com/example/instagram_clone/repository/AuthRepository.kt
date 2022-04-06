package com.example.instagram_clone.repository

import com.example.instagram_clone.api.ApiInstance
import com.example.instagram_clone.api.auth.body.LoginBody
import com.example.instagram_clone.api.auth.body.RegisterBody
import com.example.instagram_clone.api.auth.body.ResendOtpBody
import com.example.instagram_clone.api.auth.body.VerifyOtpBody
import com.example.instagram_clone.api.auth.response.LoginResponse
import com.example.instagram_clone.api.auth.response.RegisterResponse
import com.example.instagram_clone.api.auth.response.ResendOtpResponse
import com.example.instagram_clone.api.auth.response.VerifyOtpResponse
import retrofit2.Response

class AuthRepository {

    suspend fun login(body:LoginBody):Response<LoginResponse>{
        return ApiInstance.authApi.login(body)
    }

    suspend fun register(body:RegisterBody):Response<RegisterResponse>{
        return ApiInstance.authApi.register(body)
    }

    suspend fun verifyOtp(body:VerifyOtpBody):Response<VerifyOtpResponse>{
        return ApiInstance.authApi.verifyOtp(body)
    }

    suspend fun resendOtp(body:ResendOtpBody):Response<ResendOtpResponse>{
        return ApiInstance.authApi.resendOtp(body)
    }
}