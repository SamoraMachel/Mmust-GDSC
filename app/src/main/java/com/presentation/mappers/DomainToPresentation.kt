package com.presentation.mappers

import com.domain.models.*
import com.presentation.models.*

fun LoginDto.toPresentation() : LoginPresentation {
    return LoginPresentation(
        email, password
    )
}

fun ProfileDto.toPresentation() : ProfilePresentation {
    val profile = ProfilePresentation(
        profileImage, name, title, profession, description, instagram, twitter, linkedin, github, behance, dribble, interests
    )
    profile.userId = userId
    return profile
}

fun RegistrationDto.toPresentation() : RegistrationPresentation {
    return RegistrationPresentation(
         email, password, profile.toPresentation()
    )
}

fun ResourceDto.toPresentation() : ResourcePresentation {
    return ResourcePresentation(
        title, link, track_title, description, level, image, isVideo
    )
}

fun EventDto.toPresentation() : EventPresentation {
    return EventPresentation(
        title, description, link, date
    )
}

fun TrackDto.toPresentation() : TrackPresentation {
    return TrackPresentation(
        title, description, image, levels, lead, day, timeRange
    )
}

fun LevelDto.toPresentation() : LevelPresentation {
    return LevelPresentation(
        title, description
    )
}

fun SessionDto.toPresentation() : SessionPresentation {
    return SessionPresentation(
        track.toPresentation(),
        profile.toPresentation()
    )
}

fun ProgressiveDataDto<String>.toPresentation() : ProgressiveDataPresentation<String> {
    return ProgressiveDataPresentation(
        progress, data
    )
}