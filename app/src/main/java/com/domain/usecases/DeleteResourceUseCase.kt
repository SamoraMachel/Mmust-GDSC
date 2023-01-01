package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.repository.ResourceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteResourceUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository
) {
    suspend operator fun invoke(id: String): Flow<ObserverDto<Boolean>> {
        return resourceRepository.deleteResource(id)
    }
}