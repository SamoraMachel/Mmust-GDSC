package com.domain.usecases

import com.domain.models.EventDto
import com.domain.models.ObserverDto
import com.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(event : EventDto): Flow<ObserverDto<Boolean>> {
        return eventRepository.addEvent(event)
    }
}