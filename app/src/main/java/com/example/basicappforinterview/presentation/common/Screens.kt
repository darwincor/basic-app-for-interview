package com.example.basicappforinterview.presentation.common

import kotlinx.serialization.Serializable

@Serializable
object VideosScreen

@Serializable
data class VideoDetailsScreen(
    val videoId: String
)