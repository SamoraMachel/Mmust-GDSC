package com.app

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    const val DATASTORE_NAME = "gdsc_datastore"

    val IS_USER_LOGGED_IN = booleanPreferencesKey("isUserLoggedIn")
    val PROFILE_AVAILABLE = booleanPreferencesKey("profileAvailable")
    val EMAIL_ADDRESS = stringPreferencesKey("emailAddress")
}