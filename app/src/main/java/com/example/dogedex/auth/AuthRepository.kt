package com.example.dogedex.auth

import com.example.dogedex.api.ApiResponseStatus
import com.example.dogedex.api.DogsApi.retrofitService
import com.example.dogedex.api.dto.LoginDTO
import com.example.dogedex.api.dto.SignUpDTO
import com.example.dogedex.api.dto.UserDTOMapper
import com.example.dogedex.api.makeNetworkCall
import com.example.dogedex.domain.User
import kotlin.math.log
import kotlin.math.sign

class AuthRepository {
    suspend fun signUp(
        email: String,
        pass: String,
        passConfirm: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val signUpDTO = SignUpDTO(email, pass, passConfirm)
        val signUpResponse = retrofitService.signUp(signUpDTO)

        if (!signUpResponse.isSuccess) {
            throw Exception(signUpResponse.message)
        }

        val userDTO = signUpResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOtoUserDomain(userDTO)
    }

    suspend fun login(
        email: String,
        pass: String,
    ): ApiResponseStatus<User> = makeNetworkCall {
        val loginDTO = LoginDTO(email, pass)
        val loginResponse = retrofitService.login(loginDTO)

        if (!loginResponse.isSuccess) {
            throw Exception(loginResponse.message)
        }

        val userDTO = loginResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOtoUserDomain(userDTO)
    }
}