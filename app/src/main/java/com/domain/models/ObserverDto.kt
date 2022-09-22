package com.domain.models

sealed class ObserverDto<T>(
    val data : T? = null,
    val message : String? = null
) {
    class Success<T>(data: T?) : ObserverDto<T>(data)
    class Loading<T>(data: T? = null) : ObserverDto<T>(data)
    class Failure<T>(
        networkError: Boolean,
        message: String?,
    ) : ObserverDto<T>(null, message)
}