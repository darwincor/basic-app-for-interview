package com.example.basicappforinterview.data.datasource

import com.example.basicappforinterview.data.model.video.VideoDto
import com.example.basicappforinterview.data.model.videoDetails.VideoDetailsDto

import com.example.basicappforinterview.domain.util.AppError
import com.example.basicappforinterview.domain.util.Result

interface RemoteDataSource {
    suspend fun getVideos(): Result<List<VideoDto>, AppError.Network>
    suspend fun getVideoDetails(videoId: String): Result<VideoDetailsDto, AppError.Network>
}