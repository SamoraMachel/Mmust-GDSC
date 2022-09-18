package com.domain.repository

import com.domain.models.ObserverDto
import com.domain.models.TrackDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TrackRepository {
    suspend fun getTracks() : Flow<ObserverDto<List<TrackDto>>>
}