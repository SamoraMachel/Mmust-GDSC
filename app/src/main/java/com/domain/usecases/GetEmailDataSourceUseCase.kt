package com.domain.usecases

import com.domain.repository.UserDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEmailDataSourceUseCase @Inject constructor(
    private val userDataStore: UserDataStore
){
    suspend operator fun invoke(): Flow<String> {
        return userDataStore.getEmailAddress()
    }
}