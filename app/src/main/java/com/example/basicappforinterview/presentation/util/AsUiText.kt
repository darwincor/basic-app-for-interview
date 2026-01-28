package com.example.basicappforinterview.presentation.util

import com.example.basicappforinterview.R
import com.example.basicappforinterview.domain.util.AppError

fun AppError.asUiText(): UiText {
    return when(this) {
        AppError.Network.SERVICE_UNAVAILABLE -> UiText.StringResource(R.string.error_service_unavailable)
        AppError.Network.CLIENT_ERROR -> UiText.StringResource(R.string.error_client)
        AppError.Network.SERVER_ERROR -> UiText.StringResource(R.string.error_server)
        AppError.Network.UNKNOWN -> UiText.StringResource(R.string.error_unknown)
        AppError.Database.NOT_FOUND -> UiText.DynamicString("Not found in database")
        AppError.Database.DISK_FULL -> UiText.DynamicString("Disk full")
        AppError.Database.UNKNOWN -> UiText.StringResource(R.string.error_unknown)
    }
}
