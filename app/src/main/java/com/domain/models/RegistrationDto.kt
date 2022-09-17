package com.domain.models

data class RegistrationDto(
    val profileImage: String,
    val email : String,
    val password : String,
    val fullName : String,
    val interests : List<String>,
    val title : String = "Member",
    val profession : String,
    val twitter : String?,
    val linkedin : String?,
    val github : String?,
    val behance : String?,
    val dribble : String?,
)