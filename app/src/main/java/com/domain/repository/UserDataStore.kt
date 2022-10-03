package com.domain.repository

import androidx.datastore.preferences.core.Preferences
import com.domain.models.ProfileDto
import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    suspend fun saveUserLoggedInState(state : Boolean)
    suspend fun getUserLoggedInState() : Flow<Boolean>

    suspend fun saveEmailAddress(email : String)
    suspend fun getEmailAddress() : Flow<String>

    suspend fun saveProfileCreatedState(state : Boolean)
    suspend fun getProfileCreatedState() : Flow<Boolean>

    suspend fun saveBooleanDataStore(state : Boolean, preferenceKey : Preferences.Key<Boolean>)
    suspend fun getBooleanDataStore(preferenceKey: Preferences.Key<Boolean>) : Flow<Boolean>

    suspend fun saveStringDataStore(state: String, preferenceKey: Preferences.Key<String>)
    suspend fun getStringDataStore(preferenceKey: Preferences.Key<String>) : Flow<String>
}