package com.example.dogedex.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.domain.User
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {


    private val authRepository = AuthRepository()
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _status = MutableLiveData<ApiResponseStatus<User>>()
    val status: LiveData<ApiResponseStatus<User>>
        get() = _status

    fun signUp(email: String, pass: String, passConfirm: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            val user = authRepository.signUp(email, pass, passConfirm)
            handleResponseStatus(user)
        }
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _status.value = ApiResponseStatus.Loading()
            val user = authRepository.login(email, pass)
            handleResponseStatus(user)
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            _user.value = apiResponseStatus.data
        }
        _status.value = apiResponseStatus
    }
}