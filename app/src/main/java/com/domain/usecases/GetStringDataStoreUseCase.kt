package com.domain.usecases

import androidx.datastore.preferences.core.Preferences
import com.domain.repository.UserDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStringDataStoreUseCase @Inject constructor(
    private val userDataStore: UserDataStore
) {
    suspend operator fun invoke(preferenceKey : Preferences.Key<String>): Flow<String> {
        return userDataStore.getStringPreference(preferenceKey)
    }
}