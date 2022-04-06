package com.example.instagram_clone.api.auth.response

import com.example.instagram_clone.models.Tokens
import com.example.instagram_clone.models.User

data class VerifyOtpResponse(
    val success:Boolean,
    val user:User,
    val tokens:Tokens
)
