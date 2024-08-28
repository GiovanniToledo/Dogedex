package com.example.dogedex.api

import com.example.dogedex.DOGS_GET_ENDPOINT
import com.example.dogedex.SIGN_IN_ENDPOINT
import com.example.dogedex.SIGN_UP_ENDPOINT
import com.example.dogedex.URL_BASE
import com.example.dogedex.api.dto.LoginDTO
import com.example.dogedex.api.dto.SignUpDTO
import com.example.dogedex.api.responses.DogListApiResponse
import com.example.dogedex.api.responses.AuthApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofit = Retrofit.Builder()
    .baseUrl(URL_BASE)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService {

    @GET(DOGS_GET_ENDPOINT)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_ENDPOINT)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST(SIGN_IN_ENDPOINT)
    suspend fun login(@Body loginDTO: LoginDTO): AuthApiResponse
}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}