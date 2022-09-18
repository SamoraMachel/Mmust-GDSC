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
        profileImage,
        name,
        title,
        profession,
        description,
        twitter,
        linkedin,
        github,
        behance,
        dribble
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
        profileImage,
        email,
        password,
        fullName,
        description,
        interests,
        title,
        profession,
        twitter,
        linkedin,
        github,
        behance,
        dribble
    )
}

fun Track.toDomain() : TrackDto {
    return TrackDto(
        title,
        description,
        image
    )
}