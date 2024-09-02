package com.example.dogedex.api

import com.example.dogedex.ADD_DOG_TO_USER_ENDPOINT
import com.example.dogedex.DOGS_GET_ENDPOINT
import com.example.dogedex.GET_DOG_BY_ML_ID_ENDPOINT
import com.example.dogedex.GET_USER_DOGS_ENDPOINT
import com.example.dogedex.SIGN_IN_ENDPOINT
import com.example.dogedex.SIGN_UP_ENDPOINT
import com.example.dogedex.URL_BASE
import com.example.dogedex.api.dto.AddDogToUserDTO
import com.example.dogedex.api.dto.LoginDTO
import com.example.dogedex.api.dto.SignUpDTO
import com.example.dogedex.api.responses.AuthApiResponse
import com.example.dogedex.api.responses.DefaultResponse
import com.example.dogedex.api.responses.DogApiResponse
import com.example.dogedex.api.responses.DogListApiResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

private val okHttpClient = OkHttpClient
    .Builder()
    .addInterceptor(ApiServiceInterceptor)
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
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

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER_ENDPOINT)
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET(GET_USER_DOGS_ENDPOINT)
    suspend fun getUserDogs(): DogListApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET(GET_DOG_BY_ML_ID_ENDPOINT)
    suspend fun getDogByMlId(@Query("ml_id") mlId: String): DogApiResponse
}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}