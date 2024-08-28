package com.example.dogedex.api

import com.example.dogedex.Dog

sealed class ApiResponseStatus<T> {
    class Success<T>(val data: T) : ApiResponseStatus<T>()
    class Error<T>(val message: String) : ApiResponseStatus<T>()
    class Loading<T>: ApiResponseStatus<T>()
}
