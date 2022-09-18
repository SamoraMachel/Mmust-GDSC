package com.presentation.models

data class TrackPresentation(
    val title : String,
    val description : String,
    val image : String,
    val levels : Map<String, String>,
    val lead : String,
)