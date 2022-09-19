package com.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrackPresentation(
    val title : String,
    val description : String,
    val image : String,
    val levels : Map<String, String>,
    val lead : String,
) : Parcelable