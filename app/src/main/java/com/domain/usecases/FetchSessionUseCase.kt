package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.SessionDto
import com.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchSessionUseCase @Inject constructor(
    private val trackRepository : TrackRepository
){
    suspend operator fun invoke(): Flow<ObserverDto<List<SessionDto>>> {
        return trackRepository.getSessions()
    }
}