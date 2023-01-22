package com.presentation.mappers

import com.domain.models.ProfileDto
import com.domain.models.RegistrationDto
import com.domain.models.ResourceDto
import com.domain.models.TrackDto
import com.presentation.models.ProfilePresentation
import com.presentation.models.RegistrationPresentation
import com.presentation.models.ResourcePresentation
import com.presentation.models.TrackPresentation

fun RegistrationPresentation.toDto(): RegistrationDto {
    return RegistrationDto(
        email,
        password,
        profile.toDto()
    )
}
fun ProfilePresentation.toDto(): ProfileDto {
    val profile = ProfileDto(
        profileImage, name, title, profession, description, instagram, twitter, linkedin, github, behance, dribble, interests
    )
    profile.userId = userId
    return profile
}

fun TrackPresentation.toDto(): TrackDto {
    val track = TrackDto(title, description, image, levels, lead, day, timeRange)
    track.trackId = trackId
    return track
}

fun ResourcePresentation.toDto(): ResourceDto {
    return ResourceDto(
        title, link, track_title, description, level, image, isVideo
    )
}