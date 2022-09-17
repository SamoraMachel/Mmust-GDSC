package com.domain.repository

import com.domain.models.SessionDto

interface SessionRepository {
    fun getUpcomingSession() : List<SessionDto>

    fun getPastSession() : List<SessionDto>
}