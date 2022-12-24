package com.domain.usecases

import com.domain.repository.UserDataStore
import javax.inject.Inject

class SaveLoginDataStoreUseCase @Inject constructor(
    private val userDataStore: UserDataStore
) {
    suspend operator fun invoke(state : Boolean) {
        userDataStore.saveUserLoggedInState(state)
    }
}