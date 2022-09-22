package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.RegistrationDto
import com.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke(regModel : RegistrationDto) : Flow<ObserverDto<Boolean>> {
        return authenticationRepository.registerUser(regModel)
    }
}