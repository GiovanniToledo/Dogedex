package com.example.dogedex.api

import com.example.dogedex.R
import com.example.dogedex.api.DogsApi.retrofitService
import com.example.dogedex.api.dto.DogDTOMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException

private const val UNAUTHORIZED_ERROR_CODE = 401

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
): ApiResponseStatus<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResponseStatus.Success(call())
        } catch (unknownHostExc: UnknownHostException) {
            ApiResponseStatus.Error(R.string.error_showing_dog_not_found)
        } catch (exc: Exception) {
            val errMsg = when(exc.message) {
                "sign_up_error" -> R.string.error_signing_up
                "sign_in_error" -> R.string.error_loging_in
                "user_already_exist" -> R.string.user_already_exist
                "error_adding_dog" -> R.string.error_adding_dog
                else -> R.string.unknown_error
            }
            ApiResponseStatus.Error(errMsg)
        } catch (httpExc: HttpException) {
            val errorMsg = if(httpExc.code() == 401) R.string.wrong_user_or_pass else R.string.unknown_error
            ApiResponseStatus.Error(errorMsg)
        }
    }
}