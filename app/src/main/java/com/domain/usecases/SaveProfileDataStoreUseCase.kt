package com.domain.usecases

import com.domain.repository.UserDataStore
import javax.inject.Inject

class SaveProfileDataStoreUseCase @Inject constructor(
    private val dataStore: UserDataStore
) {
    suspend operator fun invoke(state: Boolean) {
        dataStore.saveProfileCreatedState(state)
    }
}