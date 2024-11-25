package com.example.basicappforinterview.data.model.videoDetails

import com.google.gson.annotations.SerializedName

data class VideoDetailsDto(
    @SerializedName("backdrop_path") val backdropPath: String,
    val id: Int,
    @SerializedName("original_title") val originalTitle: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String,
)