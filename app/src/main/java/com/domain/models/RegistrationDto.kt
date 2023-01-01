package com.domain.models

data class RegistrationDto(
    val email : String,
    val password : String,
    val profile : ProfileDto
)