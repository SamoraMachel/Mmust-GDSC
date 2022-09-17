package com.domain.usecases

import com.domain.repository.SessionRepository
import javax.inject.Inject

class UpcomingSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
){
    suspend fun invoke() {
        return sessionRepository.getUpcomingSession()
    }
}