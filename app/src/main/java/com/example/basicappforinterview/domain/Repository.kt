package com.example.basicappforinterview.domain

import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.domain.model.VideoDetails
import com.example.basicappforinterview.domain.util.AppError
import com.example.basicappforinterview.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getVideos(): Result<List<Video>, AppError>
    suspend fun getVideoDetails(videoId: String): Result<VideoDetails, AppError>

    fun getFavoriteVideos(): Flow<List<Video>>
    suspend fun insertFavoriteVideo(video: Video)
    suspend fun deleteFavoriteVideo(video: Video)
    suspend fun isFavorite(videoId: Int): Boolean
}