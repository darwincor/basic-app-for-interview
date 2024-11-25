package com.example.basicappforinterview.data.model.video

import com.google.gson.annotations.SerializedName

data class VideoDto(
    val id: Int,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("poster_path") val posterPath: String,
)