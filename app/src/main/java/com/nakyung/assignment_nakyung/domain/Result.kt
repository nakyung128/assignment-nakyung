package com.nakyung.assignment_nakyung.domain

sealed class Result<out T> {
    data class Success<out T>(
        val data: T,
    ) : Result<T>()

    data class Error(
        val message: String,
    ) : Result<Nothing>()

    data object Loading : Result<Nothing>()
}
