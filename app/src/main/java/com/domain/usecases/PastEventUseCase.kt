package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.EventDto
import com.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class PastEventUseCase @Inject constructor(
    private val sessionRepository: EventRepository
){
    suspend fun invoke() : Flow<ObserverDto<List<EventDto>>> {
        return sessionRepository.getPastSession()
    }
}