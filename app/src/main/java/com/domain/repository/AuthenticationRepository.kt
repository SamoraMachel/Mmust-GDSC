package com.domain.repository

import com.domain.models.LoginDto
import com.domain.models.ObserverDto
import com.domain.models.RegistrationDto
import kotlinx.coroutines.flow.StateFlow

interface AuthenticationRepository {
    suspend fun loginUser(loginModel : LoginDto) : StateFlow<ObserverDto<Boolean>>

    suspend fun registerUser(regModel : RegistrationDto) : StateFlow<ObserverDto<Boolean>>

    suspend fun logoutUser() : StateFlow<ObserverDto<Boolean>>
}