package com.example.instagram_clone.network.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagram_clone.api.auth.body.LoginBody
import com.example.instagram_clone.api.auth.response.LoginResponse
import com.example.instagram_clone.repository.AuthRepository
import com.example.instagram_clone.utils.Resource
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
                _login.value = Resource.Error(response.message(),response.code())
            }
        }
    }

}