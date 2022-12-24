package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.RegistrationDto
import com.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
){
    suspend operator fun invoke(registrationModel : RegistrationDto) : Flow<ObserverDto<Boolean>> {
        return authenticationRepository.registerUser(registrationModel)
    }
}