package com.presentation.models

data class ProfilePresentation(
    val profileImage : String,
    val name : String,
    val title : String,
    val profession : String,
    val description : String?,
    val twitter : String?,
    val linkedin : String?,
    val github : String?,
    val behance : String?,
    val dribble : String?,
)
