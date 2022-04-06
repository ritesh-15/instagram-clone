package com.example.instagram_clone.api.auth

import com.example.instagram_clone.api.auth.body.LoginBody
import com.example.instagram_clone.api.auth.body.RegisterBody
import com.example.instagram_clone.api.auth.body.ResendOtpBody
import com.example.instagram_clone.api.auth.body.VerifyOtpBody
import com.example.instagram_clone.api.auth.response.LoginResponse
import com.example.instagram_clone.api.auth.response.RegisterResponse
import com.example.instagram_clone.api.auth.response.ResendOtpResponse
import com.example.instagram_clone.api.auth.response.VerifyOtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthInterface {

    @POST("auth/login")
    suspend fun login(
        @Body body: LoginBody
    ):Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(
        @Body body:RegisterBody
    ):Response<RegisterResponse>

    @POST("auth/verify-otp")
    suspend fun verifyOtp(
        @Body body:VerifyOtpBody
    ):Response<VerifyOtpResponse>

    @POST("auth/resend-otp")
    suspend fun resendOtp(
        @Body body:ResendOtpBody
    ):Response<ResendOtpResponse>

}