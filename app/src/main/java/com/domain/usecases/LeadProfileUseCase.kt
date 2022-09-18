package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.ProfileDto
import com.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LeadProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke() : Flow<ObserverDto<List<ProfileDto>>> {
        return profileRepository.getLeads()
    }
}