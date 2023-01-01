package com.domain.usecases

import com.domain.models.EventDto
import com.domain.models.ObserverDto
import com.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(id: String, event: EventDto): Flow<ObserverDto<Boolean>> {
        return eventRepository.editEvent(id, event)
    }
}