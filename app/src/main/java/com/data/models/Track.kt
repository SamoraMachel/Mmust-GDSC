package com.data.models

data class Track(
    val title : String,
    val description : String,
    val image : String,
    val levels : Map<String, String>,
    val lead : String,
)