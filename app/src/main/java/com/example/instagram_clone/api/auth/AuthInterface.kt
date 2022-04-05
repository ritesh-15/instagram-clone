package com.example.instagram_clone.api.auth

import com.example.instagram_clone.api.auth.body.LoginBody
import com.example.instagram_clone.api.auth.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthInterface {

    @POST("/auth/login")
    suspend fun login(
        @Body body: LoginBody
    ):Response<LoginResponse>

}