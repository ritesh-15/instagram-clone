package com.example.instagram_clone.api.auth.response

data class ResendOtpResponse(
    val success:Boolean,
    val hash:String,
    val email:String
)
