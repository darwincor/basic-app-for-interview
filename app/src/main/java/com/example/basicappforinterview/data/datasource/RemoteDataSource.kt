package com.example.basicappforinterview.data.datasource

import com.example.basicappforinterview.data.model.video.VideoDto
import com.example.basicappforinterview.data.model.videoDetails.VideoDetailsDto

interface RemoteDataSource {
    suspend fun getVideos(): List<VideoDto>
    suspend fun getVideoDetails(videoId: String): VideoDetailsDto
}