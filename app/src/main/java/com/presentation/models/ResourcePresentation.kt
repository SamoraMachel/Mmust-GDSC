package com.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResourcePresentation(
    val title : String,
    val link : String,
    val track_title : String,
    val description : String,
    val level : String,
    val image : String?,
    val isVideo : Boolean = false
) : Parcelable