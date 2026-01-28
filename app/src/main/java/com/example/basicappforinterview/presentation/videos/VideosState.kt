package com.example.basicappforinterview.presentation.videos

import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.presentation.util.UiText

data class VideosState(
    val videos: List<Video> = emptyList(),
    val favoriteVideos: List<Video> = emptyList(),
    val isLoading: Boolean = true,
    val error: UiText? = null
)

