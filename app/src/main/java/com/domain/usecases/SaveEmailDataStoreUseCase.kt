package com.domain.usecases

import com.domain.repository.UserDataStore
import javax.inject.Inject

class SaveEmailDataStoreUseCase @Inject constructor(
    private val userDataStore: UserDataStore
){
    suspend operator fun invoke(email : String) {
        return userDataStore.saveEmailAddress(email)
    }
}