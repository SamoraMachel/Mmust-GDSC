package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(id: String): Flow<ObserverDto<Boolean>> {
        return eventRepository.deleteEvent(id)
    }
}