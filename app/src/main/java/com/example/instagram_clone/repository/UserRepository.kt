package com.example.instagram_clone.repository

import com.example.instagram_clone.api.ApiInstance
import com.example.instagram_clone.api.users.body.ActivateBody
import com.example.instagram_clone.api.users.body.ChangeUsernameBody
import com.example.instagram_clone.api.users.response.ActivateResponse
import com.example.instagram_clone.api.users.response.ChangeUserNameResponse
import retrofit2.Response

class UserRepository {

    suspend fun activate(body:ActivateBody):Response<ActivateResponse>{
        return ApiInstance.userApi.activate(body)
    }


    suspend fun changeUsername(body:ChangeUsernameBody):Response<ChangeUserNameResponse>{
        return ApiInstance.userApi.changeUserName(body)
    }

}