package com.oc.models

sealed class Result<T> {

    class Success<T>(val data: T) : Result<T>()
    class Error<T>(val cause: String? = null) : Result<T>()
    class Loading<T> : Result<T>()

}