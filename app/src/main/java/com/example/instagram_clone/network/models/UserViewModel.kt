package com.example.instagram_clone.network.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.instagram_clone.api.users.body.ActivateBody
import com.example.instagram_clone.api.users.body.ChangeUsernameBody
import com.example.instagram_clone.api.users.response.ActivateResponse
import com.example.instagram_clone.api.users.response.ChangeUserNameResponse
import com.example.instagram_clone.repository.UserRepository
import com.example.instagram_clone.utils.Resource
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
):ViewModel() {

    // activate response
    private var _activate:MutableLiveData<Resource<ActivateResponse>> = MutableLiveData()
    val activate:LiveData<Resource<ActivateResponse>>
        get() = _activate

    fun activate(body:ActivateBody){
        _activate.value = Resource.Loading()
        viewModelScope.launch {
            val response = repository.activate(body)
            if(response.isSuccessful){
                _activate.value = Resource.Success(response.body())
            }else{
                _activate.value = Resource.Error(response.errorBody())
            }
        }
    }

    // change username response
    private var _changeUsername:MutableLiveData<Resource<ChangeUserNameResponse>> = MutableLiveData()
    val changeUsername:LiveData<Resource<ChangeUserNameResponse>>
        get() = _changeUsername

    fun changeUsername(body:ChangeUsernameBody){
        _changeUsername.value = Resource.Loading()
        viewModelScope.launch {
            val response = repository.changeUsername(body)
            if(response.isSuccessful){
                _changeUsername.value = Resource.Success(response.body())
            }else{
                _changeUsername.value = Resource.Error(response.errorBody())
            }
        }
    }

}