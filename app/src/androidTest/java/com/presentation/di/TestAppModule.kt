package com.presentation.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.data.repository.*
import com.domain.repository.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Singleton
    fun providesAuthenticationRepository(firebaseAuth : FirebaseAuth, firebaseFirestore: FirebaseFirestore) : AuthenticationRepository {
        return AuthenticationRepositoryImpl(firebaseAuth,  firebaseFirestore)
    }

    @Provides
    @Singleton
    fun providesProfileRepository(firebaseFirestore: FirebaseFirestore) : ProfileRepository {
        return ProfileRepositoryImpl(firebaseFirestore)
    }

    @Provides
    @Singleton
    fun providesResourceRepository(firebaseFirestore: FirebaseFirestore) : ResourceRepository {
        return ResourceRepositoryImpl(firebaseFirestore)
    }

    @Provides
    @Singleton
    fun providesEventRepository(firebaseFirestore: FirebaseFirestore) : EventRepository {
        return EventRepositoryImpl(firebaseFirestore)
    }

    @Provides
    @Singleton
    fun providesTrackRepository(firebaseFirestore: FirebaseFirestore, profileRepository: ProfileRepository) : TrackRepository {
        return TrackRepositoryImpl(firebaseFirestore, profileRepository)
    }

    @Provides
    @Singleton
    fun providesSharedPreference(@ApplicationContext application : Application): SharedPreferences {
        return application.getSharedPreferences("gdsc", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesUserDataStore(preferenceDataStore : DataStore<Preferences>): UserDataStoreImpl {
        return UserDataStoreImpl(preferenceDataStore)
    }
}