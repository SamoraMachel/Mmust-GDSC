package com.domain.usecases

import androidx.datastore.preferences.core.Preferences
import com.domain.repository.UserDataStore
import javax.inject.Inject

class SaveStringDataStoreUseCase @Inject constructor(
    private val userDataStore: UserDataStore
) {
    suspend operator fun invoke(data: String, preferenceKey: Preferences.Key<String>) {
        userDataStore.saveStringPreference(data, preferenceKey)
    }
}