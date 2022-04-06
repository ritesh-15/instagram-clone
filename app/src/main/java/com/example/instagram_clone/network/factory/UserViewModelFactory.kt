package com.example.instagram_clone.network.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.instagram_clone.network.models.UserViewModel
import com.example.instagram_clone.repository.UserRepository

class UserViewModelFactory(
    private val repository: UserRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }
}