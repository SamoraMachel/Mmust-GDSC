package com.domain.models

data class ResourceDto(
    val title : String,
    val link : String,
    val track_title : String,
    val description : String,
    val level : String,
    val image : String?,
    val isVideo : Boolean = false
)
