package com.domain.usecases

import com.data.repository.FakeProfileRepository
import com.domain.models.ProfileDto
import org.junit.Before

open class ProfileSetup {
    lateinit var fakeRepository : FakeProfileRepository

    @Before
    open fun setUp() {
        fakeRepository = FakeProfileRepository()

        val profileToInsert = mutableListOf<ProfileDto>()
        ('a'..'z').forEachIndexed { index, c ->
            profileToInsert.add(
                ProfileDto(
                    "",
                    c.toString(),
                    c.toString(),
                    c.toString(),
                    c.toString(),
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    listOf()
                )
            )
        }
        fakeRepository.insertProfiles(profileToInsert)
    }
}