package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.ResourceDto
import com.domain.repository.ResourceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ResourceLevelUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository
){
    suspend operator fun invoke(level : String, track: String) : Flow<ObserverDto<List<ResourceDto>>> {
        return resourceRepository.getResourceByLevel(level, track)
    }
}