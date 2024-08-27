package com.example.dogedex.api

import com.example.dogedex.DOGS_GET_ENDPOINT
import com.example.dogedex.Dog
import com.example.dogedex.URL_BASE
import com.example.dogedex.api.responses.DogListApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .baseUrl(URL_BASE)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService {

    @GET(DOGS_GET_ENDPOINT)
    suspend fun getAllDogs(): DogListApiResponse
}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}