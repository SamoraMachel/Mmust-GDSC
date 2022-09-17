package com.domain.repository

import com.domain.models.ObserverDto
import com.domain.models.ProfileDto
import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    suspend fun getProfiles() : StateFlow<ObserverDto<List<ProfileDto>>>

    suspend fun getLeads() : StateFlow<ObserverDto<List<ProfileDto>>>

    suspend fun getMember() : StateFlow<ObserverDto<List<ProfileDto>>>

    suspend fun getMemberByProfession(profession : String) : StateFlow<ObserverDto<List<ProfileDto>>>
}