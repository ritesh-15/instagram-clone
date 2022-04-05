package com.example.instagram_clone.utils

 sealed class Resource<T>(
    val data:T? = null,
    val message:String? = null,
    val status:Int? = null
) {

    class Success<T>(data:T?, message:String? = null, status:Int? = null):Resource<T>(data,message, status)

    class Loading<T>(data:T? = null, message: String? = null, status: Int? = null):Resource<T>(data, message, status)

     class Error<T>(message:String?, status:Int? = null):Resource<T>(null,message, status)
}