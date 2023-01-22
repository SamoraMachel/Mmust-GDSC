package com.data.mappers

import com.data.models.*
import com.domain.models.*

fun Resource.toDomain() : ResourceDto {
    return ResourceDto(
        title,
        link,
        track_title,
        description,
        level,
        image,
        isVideo
    )
}

fun Event.toDomain() : EventDto {
    return EventDto(
        title,
        description,
        link,
        date
    )
}

fun Profile.toDomain() : ProfileDto {
    return ProfileDto(
        profileImage, name, title, profession, description, instagram, twitter, linkedin, github, behance, dribble, interests
    )
}

fun Login.toDomain() : LoginDto {
    return LoginDto(
        email,
        password
    )
}

fun Registration.toDomain() : RegistrationDto {
    return RegistrationDto(
        email,
        password,
        profile.toDomain()
    )
}

fun Track.toDomain() : TrackDto {
    val track = TrackDto(
        title,
        description,
        image,
        levels,
        lead,
        day,
        timeRange
    )
    track.trackId = trackId
    return track
}