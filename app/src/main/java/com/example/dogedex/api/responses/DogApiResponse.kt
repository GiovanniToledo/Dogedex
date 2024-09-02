package com.example.dogedex.api.responses

import com.example.dogedex.api.dto.DogDTO
import com.squareup.moshi.Json

class DogApiResponse(
    val message: String,
    @field:Json(name = "is_success") val isSuccess: Boolean,
    val data: DogResponse
)