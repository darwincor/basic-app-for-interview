package com.example.basicappforinterview.presentation.videodetails

data class VideoDetailsState(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val duration: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)