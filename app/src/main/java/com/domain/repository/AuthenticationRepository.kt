package com.domain.repository

import com.domain.models.LoginDto
import com.domain.models.ObserverDto
import com.domain.models.RegistrationDto

interface AuthenticationRepository {
    fun loginUser(loginModel : LoginDto) : ObserverDto<Boolean>

    fun registerUser(regModel : RegistrationDto) : ObserverDto<Boolean>
}