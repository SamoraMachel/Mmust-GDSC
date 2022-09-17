package com.domain.repository

import com.domain.models.ObserverDto
import com.domain.models.SessionDto
import kotlinx.coroutines.flow.StateFlow

interface SessionRepository {
    suspend fun getUpcomingSession() : StateFlow<ObserverDto<List<SessionDto>>>

    suspend fun getPastSession() : StateFlow<ObserverDto<List<SessionDto>>>
}