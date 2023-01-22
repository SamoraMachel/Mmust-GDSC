package com.domain.models

data class TrackDto(
    val title : String,
    val description : String,
    val image : String,
    val levels : Map<String, String>,
    val lead : String,
    val day : String,
    val timeRange : String
) {
    lateinit var trackId: String
}