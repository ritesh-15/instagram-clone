package com.example.instagram_clone.api.users.response

import com.example.instagram_clone.models.User

data class ChangeUserNameResponse(
    val success:Boolean,
    val user:User
)
