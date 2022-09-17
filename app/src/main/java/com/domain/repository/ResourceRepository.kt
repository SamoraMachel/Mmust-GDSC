package com.domain.repository

import com.domain.models.LevelDto
import com.domain.models.ObserverDto
import com.domain.models.ResourceDto
import kotlinx.coroutines.flow.StateFlow

interface ResourceRepository {
    suspend fun getResources() : StateFlow<ObserverDto<List<ResourceDto>>>

    suspend fun getResourceByLevel(level : String) : StateFlow<ObserverDto<List<ResourceDto>>>

    suspend fun getLevels() : StateFlow<ObserverDto<List<LevelDto>>>
}