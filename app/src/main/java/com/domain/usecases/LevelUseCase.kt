package com.domain.usecases

import com.domain.models.LevelDto
import com.domain.models.ObserverDto
import com.domain.repository.ResourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LevelUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository
) {
    suspend fun invoke() : Flow<ObserverDto<List<LevelDto>>> {
        return resourceRepository.getLevels()
    }
}