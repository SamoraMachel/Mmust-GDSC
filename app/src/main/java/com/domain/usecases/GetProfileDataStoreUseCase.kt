package com.domain.usecases

import com.domain.repository.UserDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileDataStoreUseCase @Inject constructor(
    private val dataStore: UserDataStore
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return dataStore.getProfileCreatedState()
    }
}