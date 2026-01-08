package com.example.basicappforinterview.domain

import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.domain.model.VideoDetails
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getVideos(): List<Video>
    suspend fun getVideoDetails(videoId: String): VideoDetails

    fun getFavoriteVideos(): Flow<List<Video>>
    suspend fun insertFavoriteVideo(video: Video)
    suspend fun deleteFavoriteVideo(video: Video)
    suspend fun isFavorite(videoId: Int): Boolean
}