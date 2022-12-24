package com.domain.repository

import androidx.datastore.preferences.core.Preferences
import com.app.PreferenceKeys
import com.domain.models.ProfileDto
import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    suspend fun saveUserLoggedInState(state : Boolean)
    suspend fun getUserLoggedInState() : Flow<Boolean>

    suspend fun saveEmailAddress(email : String)
    suspend fun getEmailAddress() : Flow<String>

    suspend fun saveProfileCreatedState(state : Boolean)
    suspend fun getProfileCreatedState() : Flow<Boolean>

    suspend fun saveStringPreference(data : String, preferenceKey : Preferences.Key<String>)
    suspend fun getStringPreference(preferenceKey: Preferences.Key<String>) : Flow<String>

    suspend fun saveBooleanPreference(data : Boolean, preferenceKey: Preferences.Key<Boolean>)
    suspend fun getBooleanPreference(preferenceKey: Preferences.Key<Boolean>) : Flow<Boolean>
}