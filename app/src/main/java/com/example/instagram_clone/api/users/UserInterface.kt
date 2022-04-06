package com.example.instagram_clone.api.users

import com.example.instagram_clone.api.users.body.ActivateBody
import com.example.instagram_clone.api.users.body.ChangeUsernameBody
import com.example.instagram_clone.api.users.response.ActivateResponse
import com.example.instagram_clone.api.users.response.ChangeUserNameResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserInterface {

    @PUT("user/activate")
    suspend fun activate(
        @Body body:ActivateBody
    ):Response<ActivateResponse>

    @PUT("user/change-username")
    suspend fun changeUserName(
        @Body body:ChangeUsernameBody
    ):Response<ChangeUserNameResponse>

}