package com.domain.repository

import com.domain.models.ObserverDto
import com.domain.models.EventDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface EventRepository {
    suspend fun getUpcomingSession() : Flow<ObserverDto<List<EventDto>>>

    suspend fun getPastSession() : Flow<ObserverDto<List<EventDto>>>

    suspend fun addEvent(event: EventDto) : Flow<ObserverDto<Boolean>>
    suspend fun editEvent(id: String, event: EventDto) : Flow<ObserverDto<Boolean>>
    suspend fun deleteEvent(id: String) : Flow<ObserverDto<Boolean>>
}