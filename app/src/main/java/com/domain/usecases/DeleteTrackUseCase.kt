package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteTrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend operator fun invoke(id: String): Flow<ObserverDto<Boolean>> {
        return trackRepository.deleteTrack(id)
    }
}