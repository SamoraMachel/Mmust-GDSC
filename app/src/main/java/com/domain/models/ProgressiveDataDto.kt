package com.domain.models

data class ProgressiveDataDto<T>(
    var progress : Int = 0,
    var data : T? = null
)