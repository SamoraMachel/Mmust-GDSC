package com.data.repository

import com.domain.models.ObserverDto
import com.domain.models.ProfileDto
import com.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeProfileRepository : ProfileRepository {
    private val profiles = mutableListOf<ProfileDto>()

    fun insertProfiles(mProfiles : List<ProfileDto>) {
        profiles.addAll(mProfiles)
    }

    override suspend fun getProfiles(): Flow<ObserverDto<List<ProfileDto>>> {
        return flow { emit( ObserverDto.Success(profiles) ) }
    }

    override suspend fun getLeads(): Flow<ObserverDto<List<ProfileDto>>> {
        val profile_leads = mutableListOf<ProfileDto>()
        for(profile in profiles) {
            if(profile.title == "Lead")
                profile_leads.add(profile)
        }
        return flow { emit( ObserverDto.Success(profile_leads))}
    }

    override suspend fun getLead(id: String): Flow<ObserverDto<ProfileDto>> {
        var lead : ProfileDto? = null
        for(profile in profiles) {
            if (profile.name == id) {
                lead = profile
                break
            }
        }
        return flow {emit(ObserverDto.Success(lead))}
    }

    override suspend fun getMember(): Flow<ObserverDto<List<ProfileDto>>> {
        val members_profiles = mutableListOf<ProfileDto>()
        for (profile in profiles) {
            if(profile.title == "Member")
                members_profiles.add(profile)
        }
        return flow { emit(ObserverDto.Success(members_profiles)) }
    }

    override suspend fun getMemberByProfession(profession: String): Flow<ObserverDto<List<ProfileDto>>> {
        val member_list = mutableListOf<ProfileDto>()
        profiles.forEach { profile ->
            if(profile.profession == profession)
                member_list.add(profile)
        }
        return flow { emit(ObserverDto.Success(member_list)) }
    }

    override suspend fun getProfileByEmail(email: String): Flow<ObserverDto<ProfileDto?>> {
        return flow { ObserverDto.Success(null) }
    }

}