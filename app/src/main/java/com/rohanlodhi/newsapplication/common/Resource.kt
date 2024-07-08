package com.rohanlodhi.newsapplication.common

sealed class Resource<T>(             // Sealed class Resource with a generic type T
    val data: T? = null,              // Property to hold data of type T, initialized to null
    val message: String? = null       // Property to hold an optional message of type String, initialized to null
) {
    // Subclass representing loading state
    class Loading<T>(data: T? = null) : Resource<T>(data)

    // Subclass representing successful state
    class Success<T>(data: T?) : Resource<T>(data)

    // Subclass representing error state
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
}