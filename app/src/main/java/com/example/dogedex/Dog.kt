package com.example.dogedex

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Dog(
    val id: Int,
    val index: Int,
    val name: String,
    val type: String,
    val heightFemale: String,
    val heightMale: String,
    val imageUrl: String,
    val lifeExpectancy: String,
    val temperament: String,
    val weightFemale: String,
    val weightMale: String
) : Parcelable
