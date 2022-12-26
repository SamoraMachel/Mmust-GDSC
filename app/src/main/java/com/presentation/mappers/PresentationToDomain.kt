package com.presentation.mappers

import com.domain.models.ProfileDto
import com.domain.models.RegistrationDto
import com.presentation.models.ProfilePresentation
import com.presentation.models.RegistrationPresentation

fun RegistrationPresentation.toDto(): RegistrationDto {
    return RegistrationDto(
        email,
        password,
        profile.toDto()
    )
}
fun ProfilePresentation.toDto(): ProfileDto {
    return ProfileDto(
        profileImage, name, title, profession, description, instagram, twitter, linkedin, github, behance, dribble, interests
    )
}