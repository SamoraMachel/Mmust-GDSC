package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.TrackDto
import com.domain.repository.TrackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrackByNameUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend operator fun invoke(name: String): Flow<ObserverDto<TrackDto>> {
        return trackRepository.getTrackByName(name)
    }
}