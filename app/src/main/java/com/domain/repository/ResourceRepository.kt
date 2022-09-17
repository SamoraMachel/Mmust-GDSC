package com.domain.repository

import com.domain.models.ResourceDto

interface ResourceRepository {
    fun getResources() : List<ResourceDto>

    fun getResourceByLevel(level : String) : List<ResourceDto>
}