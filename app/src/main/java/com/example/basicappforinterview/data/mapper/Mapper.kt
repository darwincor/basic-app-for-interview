package com.example.basicappforinterview.data.mapper

//From VideoDto to Video
import com.example.basicappforinterview.data.model.video.VideoDto
import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.data.model.videoDetails.VideoDetailsDto
import com.example.basicappforinterview.domain.model.VideoDetails

fun VideoDto.toDomain() = Video(
    id = id,
    title = originalTitle,
    thumbnail = "https://image.tmdb.org/t/p/w500$posterPath",
)

fun VideoDetailsDto.toDomain() = VideoDetails(
    id = id,
    title = originalTitle,
    description = overview,
    thumbnail = "https://image.tmdb.org/t/p/w500$posterPath",
)