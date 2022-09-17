package com.presentation.models

data class ResourcePresentation(
    val title : String,
    val link : String,
    val track_title : String,
    val description : String,
    val level : String,
    val image : String?,
    val isVideo : Boolean = false
)