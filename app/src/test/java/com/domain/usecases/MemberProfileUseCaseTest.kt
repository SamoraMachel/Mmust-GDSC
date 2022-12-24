package com.domain.usecases

import com.data.repository.FakeProfileRepository
import com.domain.models.ObserverDto
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


internal class MemberProfileUseCaseTest : ProfileSetup() {
    private lateinit var memberProfileUseCase: MemberProfileUseCase

    @Before
    fun initialize() {
        memberProfileUseCase = MemberProfileUseCase(fakeRepository)
    }

    @Test
    fun `Get all the members`() = runBlocking {
        memberProfileUseCase().collect { obsever ->
            when(obsever) {
                is ObserverDto.Failure -> assert(false)
                is ObserverDto.Loading -> assert(false)
                is ObserverDto.Success -> assertThat(obsever.data?.size).isEqualTo(0)
            }
        }
    }
}