package com.domain.repository

import com.domain.models.ObserverDto
import com.domain.models.ProfileDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    suspend fun getProfiles() : Flow<ObserverDto<List<ProfileDto>>>

    suspend fun getLeads() : Flow<ObserverDto<List<ProfileDto>>>

    suspend fun getLead(id : String) : Flow<ObserverDto<ProfileDto>>

    suspend fun getMember() : Flow<ObserverDto<List<ProfileDto>>>

    suspend fun getMemberByProfession(profession : String) : Flow<ObserverDto<List<ProfileDto>>>
}