package com.example.dogedex.api.dto

import com.squareup.moshi.Json

class SignUpDTO(
    val email: String,
    @field:Json(name = "password") val pass: String,
    @field:Json(name = "password_confirmation") val passConfirm: String
) {
}