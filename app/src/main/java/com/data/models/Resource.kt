package com.data.models

data class Resource(
    val title : String,
    val link : String,
    val track_title : String,
    val description : String,
    val level : String,
    val image : String?,
    val isVideo : Boolean = false
)
