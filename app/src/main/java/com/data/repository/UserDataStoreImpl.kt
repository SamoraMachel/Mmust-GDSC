package com.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.app.PreferenceKeys
import com.domain.repository.UserDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserDataStoreImpl @Inject constructor(
    private val prefsDataStore : DataStore<Preferences>
) : UserDataStore {
    override suspend fun saveUserLoggedInState(state: Boolean) {
        prefsDataStore.edit { preferences ->
            preferences[PreferenceKeys.IS_USER_LOGGED_IN] = state
        }
    }

    override suspend fun getUserLoggedInState(): Flow<Boolean>  {
       return prefsDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preference ->
                preference[PreferenceKeys.IS_USER_LOGGED_IN] ?: false
            }
    }

    override suspend fun saveEmailAddress(email: String) {
        prefsDataStore.edit { preferences ->
            preferences[PreferenceKeys.EMAIL_ADDRESS] = email
        }
    }

    override suspend fun getEmailAddress(): Flow<String> {
        return prefsDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[PreferenceKeys.EMAIL_ADDRESS] ?: ""
            }
    }

    override suspend fun saveProfileCreatedState(state: Boolean) {
        prefsDataStore.edit { preferences ->
            preferences[PreferenceKeys.PROFILE_AVAILABLE] = state
        }
    }

    override suspend fun getProfileCreatedState(): Flow<Boolean> {
        return prefsDataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[PreferenceKeys.PROFILE_AVAILABLE] ?: false
            }
    }
}