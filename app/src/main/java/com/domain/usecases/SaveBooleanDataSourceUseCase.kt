package com.domain.usecases

import androidx.datastore.preferences.core.Preferences
import com.domain.repository.UserDataStore
import javax.inject.Inject

class SaveBooleanDataSourceUseCase @Inject constructor(
    private val userDataStore: UserDataStore
){
    suspend operator fun invoke(data : Boolean, preferenceKey: Preferences.Key<Boolean>) {
        userDataStore.saveBooleanPreference(data, preferenceKey)
    }
}