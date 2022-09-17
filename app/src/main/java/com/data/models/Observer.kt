package com.data.models

sealed class Observer<T>(
    val data : T? = null,
    val message : String? = null
) {
    class Success<T>(data: T?) : Observer<T>(data)
    class Loading<T>(data: T? = null) : Observer<T>(data)
    class Failure<T>(
        networkError: Boolean,
        message: String?,
    ) : Observer<T>(null, message)
}