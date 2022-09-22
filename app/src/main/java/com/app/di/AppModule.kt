package com.app.di

import com.data.repository.*
import com.domain.repository.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
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
}