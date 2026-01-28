package com.example.basicappforinterview.domain.util

typealias RootError = Error

sealed interface Result<out D, out E: RootError> {
    data class Success<out D, out E: RootError>(val data: D): Result<D, E>
    data class Error<out D, out E: RootError>(val error: E): Result<D, E>
}

inline fun <D, E: RootError, R> Result<D, E>.map(transform: (D) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(transform(data))
    }
}

fun <D, E: RootError> Result<D, E>.asEmptyData(): Result<Unit, E> {
    return map {  }
}

inline fun <D, E: RootError> Result<D, E>.onSuccess(action: (D) -> Unit): Result<D, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}
inline fun <D, E: RootError> Result<D, E>.onError(action: (E) -> Unit): Result<D, E> {
    return when(this) {
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}
