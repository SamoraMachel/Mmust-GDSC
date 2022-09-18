package com.domain.repository

import com.domain.models.ObserverDto
import com.domain.models.EventDto
import kotlinx.coroutines.flow.StateFlow

interface EventRepository {
    suspend fun getUpcomingSession() : StateFlow<ObserverDto<List<EventDto>>>

    suspend fun getPastSession() : StateFlow<ObserverDto<List<EventDto>>>
}