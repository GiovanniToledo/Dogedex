package com.example.dogedex.api

import com.example.dogedex.Dog

sealed class ApiResponseStatus {
    class Success(val dogList: List<Dog>) : ApiResponseStatus()
    class Error(val message: String) : ApiResponseStatus()
    data object Loading : ApiResponseStatus()
}
