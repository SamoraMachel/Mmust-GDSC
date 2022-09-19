package com.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LevelPresentation(
    val title : String,
    val description : String
) : Parcelable
