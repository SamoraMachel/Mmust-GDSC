package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.TrackDto
import com.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditTrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend operator fun invoke(id: String, track: TrackDto): Flow<ObserverDto<Boolean>> {
        return trackRepository.editTrack(id, track)
    }
}