package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.SessionDto
import com.domain.repository.SessionRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class PastSessionUseCase @Inject constructor(
    private val sessionRepository: SessionRepository
){
    suspend fun invoke() : StateFlow<ObserverDto<List<SessionDto>>> {
        return sessionRepository.getPastSession()
    }
}