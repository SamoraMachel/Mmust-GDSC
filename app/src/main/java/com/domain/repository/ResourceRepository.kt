package com.domain.repository

import com.domain.models.LevelDto
import com.domain.models.ObserverDto
import com.domain.models.ResourceDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ResourceRepository {
    suspend fun getResources() : Flow<ObserverDto<List<ResourceDto>>>

    suspend fun getResourceByLevel(level : String) : Flow<ObserverDto<List<ResourceDto>>>

    suspend fun getLevels() : Flow<ObserverDto<List<LevelDto>>>
}