package com.data.mappers

import com.data.models.*
import com.domain.models.*

fun Resource.toDomain() : ResourceDto {
    return ResourceDto(
        title,
        link,
        description,
        level,
        image,
        isVideo
    )
}

fun Session.toDomain() : SessionDto {
    return SessionDto(
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
