package com.domain.repository

import com.domain.models.ObserverDto
import com.domain.models.TrackDto
import kotlinx.coroutines.flow.StateFlow

interface TrackRepository {
    suspend fun getTracks() : StateFlow<ObserverDto<List<TrackDto>>>
}