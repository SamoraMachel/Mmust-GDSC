package com.presentation.models

data class ProgressiveDataPresentation<T>(
    val progress : Int,
    val data : T?
)