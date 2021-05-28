package com.truedigital.vhealth.api.network

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val errorCode: Int = 0, val errorMessage: String = "") : Result<T>()
    class Loading<T> : Result<T>()
}
