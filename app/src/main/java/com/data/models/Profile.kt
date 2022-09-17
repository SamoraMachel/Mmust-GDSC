package com.data.models

data class Profile(
    val profileImage : String,
    val name : String,
    val title : String,
    val profession : String,
    val twitter : String?,
    val linkedin : String?,
    val github : String?,
    val behance : String?,
    val dribble : String?,
)