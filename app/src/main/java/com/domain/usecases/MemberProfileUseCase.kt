package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.ProfileDto
import com.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MemberProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
){
    suspend fun invoke() : StateFlow<ObserverDto<List<ProfileDto>>> {
        return profileRepository.getMember()
    }
}