package com.app.di

import com.data.repository.AuthenticationRepositoryImpl
import com.data.repository.ProfileRepositoryImpl
import com.domain.repository.AuthenticationRepository
import com.domain.repository.ProfileRepository
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
}