package com.example.basicappforinterview.data.datasource

import com.example.basicappforinterview.data.api.ApiClient
import com.example.basicappforinterview.data.model.video.VideoDto
import com.example.basicappforinterview.data.model.videoDetails.VideoDetailsDto
import com.example.basicappforinterview.data.util.safeApiCall
import com.example.basicappforinterview.domain.util.AppError
import com.example.basicappforinterview.domain.util.Result
import com.example.basicappforinterview.domain.util.map
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor() : RemoteDataSource {

    //TODO This should be stored in a secure place, but it's here for simplicity
    private val token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiYzJiYjNhMjM3Yzg5YjE1MDQwZTkzOGZlZWZlMmY5YSIsIm5iZiI6MTczMjQ2MDY5Mi4yNzEyODYyLCJzdWIiOiI2NzE3ODhlZDg4YmU4MjFmNjQ5YTg0ZGEiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.VfomFt-WPC2csW4tT3oWrz39rnVBuM9wIdo9ao-SDt0"
    private val apiClient = ApiClient.create(token)

    override suspend fun getVideos(): Result<List<VideoDto>, AppError.Network> {
        return safeApiCall { apiClient.getVideos() }.map { it.results }
    }

    override suspend fun getVideoDetails(videoId: String): Result<VideoDetailsDto, AppError.Network> {
        return safeApiCall { apiClient.getVideoDetails(videoId) }
    }
}