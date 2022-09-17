package com.domain.repository

import com.domain.models.ObserverDto
import com.domain.models.ResourceDto

interface ResourceRepository {
    fun getResources() : ObserverDto<List<ResourceDto>>

    fun getResourceByLevel(level : String) : ObserverDto<List<ResourceDto>>
}