package com.example.basicappforinterview.domain

import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.domain.model.VideoDetail

interface Repository {
    suspend fun getVideos(): List<Video>
    suspend fun getVideoDetail(videoId: String): VideoDetail
}