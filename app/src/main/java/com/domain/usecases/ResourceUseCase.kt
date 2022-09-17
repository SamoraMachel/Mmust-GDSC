package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.ResourceDto
import com.domain.repository.ResourceRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ResourceUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository
) {
    suspend fun invoke() : StateFlow<ObserverDto<List<ResourceDto>>> {
        return resourceRepository.getResources()
    }
}