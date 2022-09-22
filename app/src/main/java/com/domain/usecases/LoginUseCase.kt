package com.domain.usecases

import com.domain.models.LoginDto
import com.domain.models.ObserverDto
import com.domain.models.ResourceDto
import com.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginUseCase @Inject constructor (
    private val authenticationRepository : AuthenticationRepository
){
    suspend operator fun invoke(loginModel : LoginDto) : Flow<ObserverDto<Boolean>> {
        return authenticationRepository.loginUser(loginModel)
    }
}