package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.TrackDto
import com.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddTrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend operator fun invoke(track : TrackDto): Flow<ObserverDto<Boolean>> {
        return trackRepository.addTrack(track)
    }
}