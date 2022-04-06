package com.example.instagram_clone.api.auth.body

data class VerifyOtpBody(
    val email:String,
    val hash:String,
    val otp:Int
)
