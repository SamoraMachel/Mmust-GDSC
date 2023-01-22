package com.domain.repository

import com.domain.models.ObserverDto
import com.domain.models.SessionDto
import com.domain.models.TrackDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TrackRepository {
    suspend fun getTracks() : Flow<ObserverDto<List<TrackDto>>>
    suspend fun getTrackByName(name: String): Flow<ObserverDto<TrackDto>>
    suspend fun getSessions() : Flow<ObserverDto<List<SessionDto>>>

    suspend fun addTrack(track : TrackDto) : Flow<ObserverDto<Boolean>>
    suspend fun editTrack(id : String, track: TrackDto) : Flow<ObserverDto<Boolean>>
    suspend fun deleteTrack(id : String) : Flow<ObserverDto<Boolean>>
}