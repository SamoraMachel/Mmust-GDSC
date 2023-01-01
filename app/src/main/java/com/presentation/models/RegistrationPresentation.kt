package com.presentation.models

data class RegistrationPresentation(
    val email : String,
    val password : String,
    val profile: ProfilePresentation
)
