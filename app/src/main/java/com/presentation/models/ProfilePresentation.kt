package com.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfilePresentation(
    val profileImage : String,
    val name : String,
    val title : String,
    val profession : String,
    val description : String?,
    val instagram : String?,
    val twitter : String?,
    val linkedin : String?,
    val github : String?,
    val behance : String?,
    val dribble : String?,
) : Parcelable
