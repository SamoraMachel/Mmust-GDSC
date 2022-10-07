package com.domain.usecases

import com.domain.models.ObserverDto
import com.domain.models.ProfileDto
import com.google.common.truth.Truth.assertThat
import com.data.repository.FakeProfileRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LeadProfileUseCaseTest : ProfileSetup() {
    private lateinit var leadProfileUseCase : LeadProfileUseCase

    @Before
    fun initialize() {
        leadProfileUseCase = LeadProfileUseCase(fakeRepository)
    }

    @Test
    fun `Get the number of leads from the main repo`() = runBlocking {
        leadProfileUseCase().collect { observer ->
            when(observer) {
                is ObserverDto.Failure -> Unit
                is ObserverDto.Loading -> Unit
                is ObserverDto.Success -> assertThat(observer.data?.size).isEqualTo(0)
            }
        }
    }


}