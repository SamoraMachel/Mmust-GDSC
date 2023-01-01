package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.ResourceDto
import com.domain.repository.ResourceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditResourceUseCase @Inject constructor(
    private val resourceRepository: ResourceRepository
) {
    suspend operator fun invoke(id: String, resource: ResourceDto): Flow<ObserverDto<Boolean>> {
        return resourceRepository.editResource(id, resource)
    }
}