package com.domain.repository

import com.domain.models.ProfileDto

interface ProfileRepository {
    fun getProfiles() : List<ProfileDto>

    fun getLeads() : List<ProfileDto>

    fun getMember() : List<ProfileDto>

    fun getMemberByProfession(profession : String) : List<ProfileDto>
}