package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.ProfileDto
import com.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchProfileEmailUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
){
    suspend operator fun invoke(email : String): Flow<ObserverDto<ProfileDto?>> {
        return profileRepository.getProfileByEmail(email)
    }
}