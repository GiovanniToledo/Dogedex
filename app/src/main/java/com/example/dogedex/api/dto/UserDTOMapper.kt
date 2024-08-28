package com.example.dogedex.api.dto

import com.example.dogedex.domain.User

class UserDTOMapper {
    fun fromUserDTOtoUserDomain(userDTO: UserDTO) = User(
        id = userDTO.id,
        email = userDTO.email,
        authenticationToken = userDTO.authenticationToken,
    )
}