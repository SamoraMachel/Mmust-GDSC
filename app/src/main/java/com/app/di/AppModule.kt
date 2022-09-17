package com.app.di

import com.domain.repository.AuthenticationRepository
import com.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
//    @Provides
//    @Singleton
//    fun provideAuthenticationRepository() : AuthenticationRepository {
//        return
//    }
//
//    @Provides
//    @Singleton
//    fun providesProfileRepository() : ProfileRepository {
//        return
//    }
}