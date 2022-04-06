package com.example.instagram_clone.utils

import com.example.instagram_clone.models.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody

object GetResponseErrorBody {

    fun get(state:ResponseBody):ErrorResponse{
        return Gson().fromJson(state.string(), ErrorResponse::class.java)
    }

}