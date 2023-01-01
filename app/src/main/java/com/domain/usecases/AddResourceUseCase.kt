package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.ResourceDto
import com.domain.repository.ResourceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddResourceUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository
) {
    suspend operator fun invoke(resource : ResourceDto): Flow<ObserverDto<Boolean>> {
        return resourceRepository.addResource(resource)
    }
}