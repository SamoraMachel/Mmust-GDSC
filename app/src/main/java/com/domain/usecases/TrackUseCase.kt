package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.TrackDto
import com.domain.repository.TrackRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend fun invoke() : StateFlow<ObserverDto<List<TrackDto>>> {
        return trackRepository.getTracks()
    }
}