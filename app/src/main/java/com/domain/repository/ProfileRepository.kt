package com.domain.repository

import com.domain.models.ObserverDto
import com.domain.models.ProfileDto

interface ProfileRepository {
    fun getProfiles() : ObserverDto<List<ProfileDto>>

    fun getLeads() : ObserverDto<List<ProfileDto>>

    fun getMember() : ObserverDto<List<ProfileDto>>

    fun getMemberByProfession(profession : String) : ObserverDto<List<ProfileDto>>
}