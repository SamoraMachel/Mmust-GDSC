package com.domain.repository

import com.domain.models.LevelDto
import com.domain.models.ObserverDto
import com.domain.models.ResourceDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ResourceRepository {
    suspend fun getResources() : Flow<ObserverDto<List<ResourceDto>>>

    suspend fun getResourceByLevel(level : String, track: String) : Flow<ObserverDto<List<ResourceDto>>>

    suspend fun getLevels() : Flow<ObserverDto<List<LevelDto>>>

    suspend fun addResource(resource : ResourceDto) : Flow<ObserverDto<Boolean>>
    suspend fun editResource(id: String, resource: ResourceDto) : Flow<ObserverDto<Boolean>>
    suspend fun deleteResource(id: String) : Flow<ObserverDto<Boolean>>
}