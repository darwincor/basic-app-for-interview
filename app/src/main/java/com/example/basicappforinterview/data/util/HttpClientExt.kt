package com.example.basicappforinterview.data.util

import com.example.basicappforinterview.domain.util.AppError
import com.example.basicappforinterview.domain.util.Result
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Result<T, AppError.Network> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                Result.Success(body)
            } else {
                Result.Error(AppError.Network.UNKNOWN)
            }
        } else {
            when (response.code()) {
                408 -> Result.Error(AppError.Network.CLIENT_ERROR)
                in 500..599 -> Result.Error(AppError.Network.SERVER_ERROR)
                else -> Result.Error(AppError.Network.UNKNOWN)
            }
        }
    } catch (e: Exception) {
        when (e) {
            is UnknownHostException, is ConnectException, is IOException -> Result.Error(AppError.Network.SERVICE_UNAVAILABLE)
            else -> Result.Error(AppError.Network.UNKNOWN)
        }
    }
}
