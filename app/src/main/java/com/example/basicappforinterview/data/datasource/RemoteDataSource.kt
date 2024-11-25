package com.example.basicappforinterview.data.datasource

import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.domain.model.VideoDetail

interface RemoteDataSource {
    suspend fun getVideos(): List<Video>
    suspend fun getVideoDetails(videoId: String): VideoDetail
}