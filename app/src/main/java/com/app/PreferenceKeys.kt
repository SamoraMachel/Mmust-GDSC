package com.app

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    const val DATASTORE_NAME = "gdsc_datastore"

    val IS_USER_LOGGED_IN = booleanPreferencesKey("isUserLoggedIn")
    val PROFILE_AVAILABLE = booleanPreferencesKey("profileAvailable")

    val USER_EMAIL_ADDRESS = stringPreferencesKey("emailAddress")
    val USER_PROFILE_IMAGE = stringPreferencesKey("userProfileImage")
    val USER_FULL_NAME =   stringPreferencesKey("userFullName")
 }