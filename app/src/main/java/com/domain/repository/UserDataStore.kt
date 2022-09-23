package com.domain.repository

import com.domain.models.ProfileDto
import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    suspend fun saveUserLoggedInState(state : Boolean)
    suspend fun getUserLoggedInState() : Flow<Boolean>

    suspend fun saveEmailAddress(email : String)
    suspend fun getEmailAddress() : Flow<String>

    suspend fun saveProfileCreatedState(state : Boolean)
    suspend fun getProfileCreatedState() : Flow<Boolean>
}