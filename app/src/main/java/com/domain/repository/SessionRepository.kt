package com.domain.repository

import com.domain.models.ObserverDto
import com.domain.models.SessionDto

interface SessionRepository {
    fun getUpcomingSession() : ObserverDto<List<SessionDto>>

    fun getPastSession() : ObserverDto<List<SessionDto>>
}