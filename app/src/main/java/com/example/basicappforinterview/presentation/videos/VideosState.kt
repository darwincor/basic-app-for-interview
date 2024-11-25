package com.example.basicappforinterview.presentation.videos

import com.example.basicappforinterview.domain.model.Video

data class VideosState(
    val videos: List<Video> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
