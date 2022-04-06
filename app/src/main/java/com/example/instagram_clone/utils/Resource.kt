package com.example.instagram_clone.utils

import okhttp3.ResponseBody
import retrofit2.Response

sealed class Resource<T>(
    val data:T? = null,
    val message:String? = null,
    val status:Int? = null,
    val errorBody:ResponseBody? = null
) {

    class Success<T>(data:T?, message:String? = null, status:Int? = null):Resource<T>(data,message, status)

    class Loading<T>(data:T? = null, message: String? = null, status: Int? = null):Resource<T>(data, message, status)

     class Error<T>(errorBody: ResponseBody?,message:String? = null, status:Int? = null):Resource<T>(null,message, status,errorBody)
}