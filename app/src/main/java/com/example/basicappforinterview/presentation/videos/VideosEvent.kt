package com.example.basicappforinterview.presentation.videos

import com.example.basicappforinterview.domain.model.Video

sealed class VideosEvent {
    data class ToggleFavorite(val video: Video) : VideosEvent()
    data object Retry : VideosEvent()
}

