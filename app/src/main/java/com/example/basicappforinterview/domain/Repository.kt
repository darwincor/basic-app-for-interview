package com.example.basicappforinterview.domain

import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.domain.model.VideoDetails

interface Repository {
    suspend fun getVideos(): List<Video>
    suspend fun getVideoDetails(videoId: String): VideoDetails
}