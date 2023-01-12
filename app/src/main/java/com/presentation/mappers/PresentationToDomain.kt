package com.presentation.mappers

import com.domain.models.ProfileDto
import com.domain.models.RegistrationDto
import com.domain.models.TrackDto
import com.presentation.models.ProfilePresentation
import com.presentation.models.RegistrationPresentation
import com.presentation.models.TrackPresentation

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

fun TrackPresentation.toDto(): TrackDto {
    return TrackDto(title, description, image, levels, lead, day, timeRange)
}