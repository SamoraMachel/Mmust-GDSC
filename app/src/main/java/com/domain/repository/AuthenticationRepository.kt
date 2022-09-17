package com.domain.repository

import com.domain.models.LoginDto
import com.domain.models.RegistrationDto

interface AuthenticationRepository {
    fun loginUser(loginModel : LoginDto) : Boolean

    fun registerUser(regModel : RegistrationDto) : Boolean
}