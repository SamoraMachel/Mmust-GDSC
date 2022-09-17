package com.domain.usecases

import com.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor (
    private val authenticationRepository : AuthenticationRepository
){
//    operator invoke()
}