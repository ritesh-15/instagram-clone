package com.example.instagram_clone.network.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagram_clone.api.auth.body.LoginBody
import com.example.instagram_clone.api.auth.body.RegisterBody
import com.example.instagram_clone.api.auth.body.ResendOtpBody
import com.example.instagram_clone.api.auth.body.VerifyOtpBody
import com.example.instagram_clone.api.auth.response.LoginResponse
import com.example.instagram_clone.api.auth.response.RegisterResponse
import com.example.instagram_clone.api.auth.response.ResendOtpResponse
import com.example.instagram_clone.api.auth.response.VerifyOtpResponse
import com.example.instagram_clone.models.ErrorResponse
import com.example.instagram_clone.repository.AuthRepository
import com.example.instagram_clone.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.launch

class AuthViewModel(
    val repository: AuthRepository
):ViewModel() {

    // login response
    private var _login:MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val login:LiveData<Resource<LoginResponse>>
        get() = _login

    fun login(body: LoginBody){
        _login.value = Resource.Loading()
        viewModelScope.launch {
            val response = repository.login(body)
            if(response.isSuccessful){
                _login.value = Resource.Success(response.body())
            }else{
                _login.value = Resource.Error(response.errorBody())
            }
        }
    }

    // register response
    private var _register:MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val register:LiveData<Resource<RegisterResponse>>
        get() = _register

    fun register(body: RegisterBody){
        _register.value = Resource.Loading()
        viewModelScope.launch {
            val response = repository.register(body)
            if(response.isSuccessful){
                _register.value = Resource.Success(response.body())
            }else{
                _register.value = Resource.Error(response.errorBody())
            }
        }
    }

    // verify otp response
    private var _verifyOtp:MutableLiveData<Resource<VerifyOtpResponse>> = MutableLiveData()
    val verifyOtp:LiveData<Resource<VerifyOtpResponse>>
        get() = _verifyOtp

    fun verifyOtp(body: VerifyOtpBody){
        _verifyOtp.value = Resource.Loading()
        viewModelScope.launch {
            val response = repository.verifyOtp(body)
            if(response.isSuccessful){
                _verifyOtp.value = Resource.Success(response.body())
            }else{
                _verifyOtp.value = Resource.Error(response.errorBody())
            }
        }
    }


    // resend otp response
    private var _resendOtp:MutableLiveData<Resource<ResendOtpResponse>> = MutableLiveData()
    val resendOtp:LiveData<Resource<ResendOtpResponse>>
        get() = _resendOtp

    fun resendOtp(body: ResendOtpBody){
        _resendOtp.value = Resource.Loading()
        viewModelScope.launch {
            val response = repository.resendOtp(body)
            if(response.isSuccessful){
                _resendOtp.value = Resource.Success(response.body())
            }else{
                _resendOtp.value = Resource.Error(response.errorBody())
            }
        }
    }

}