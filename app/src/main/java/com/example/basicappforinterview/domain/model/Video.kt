package com.example.basicappforinterview.domain.model

data class Video(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val isFavorite: Boolean = false
)
