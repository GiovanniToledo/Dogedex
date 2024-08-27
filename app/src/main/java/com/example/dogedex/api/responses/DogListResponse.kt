package com.example.dogedex.api.responses

import com.example.dogedex.Dog
import com.example.dogedex.api.dto.DogDTO
import com.squareup.moshi.Json

class DogListResponse(val dogs: List<DogDTO>) {

}
