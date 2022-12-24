package com.domain.usecases

import androidx.datastore.preferences.core.Preferences
import com.domain.repository.UserDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooleanDataStoreUseCase @Inject constructor(
    private val userDataStore: UserDataStore
) {
    suspend operator fun invoke(preferenceKey : Preferences.Key<Boolean>): Flow<Boolean> {
        return userDataStore.getBooleanPreference(preferenceKey)
    }
}