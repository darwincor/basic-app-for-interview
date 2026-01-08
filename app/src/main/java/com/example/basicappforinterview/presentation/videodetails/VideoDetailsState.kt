package com.example.basicappforinterview.presentation.videodetails

data class VideoDetailsState(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val backdrop: String = "",
    val duration: String = "",
    val isLoading: Boolean = true,
    val error: String = ""
)