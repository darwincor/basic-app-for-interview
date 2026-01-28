package com.example.basicappforinterview.domain.util

sealed interface Error

sealed interface AppError : Error {
    enum class Network : AppError {
        SERVICE_UNAVAILABLE,
        CLIENT_ERROR,
        SERVER_ERROR,
        UNKNOWN
    }
    enum class Database : AppError {
        NOT_FOUND,
        DISK_FULL,
        UNKNOWN
    }
}
