package com.example.basicappforinterview.data.mapper

import com.example.basicappforinterview.data.local.FavoriteVideoEntity
import com.example.basicappforinterview.data.model.video.VideoDto
import com.example.basicappforinterview.data.model.videoDetails.VideoDetailsDto
import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.domain.model.VideoDetails

fun VideoDto.toDomain() = Video(
    id = id,
    title = originalTitle,
    thumbnail = "https://image.tmdb.org/t/p/w500$posterPath",
    isFavorite = false
)

fun VideoDetailsDto.toDomain() = VideoDetails(
    id = id,
    title = originalTitle,
    description = overview,
    thumbnail = "https://image.tmdb.org/t/p/w500$posterPath",
    backdrop = "https://image.tmdb.org/t/p/w500$backdropPath",
)

fun Video.toEntity() = FavoriteVideoEntity(
    id = id,
    title = title,
    thumbnail = thumbnail
)

fun FavoriteVideoEntity.toDomain() = Video(
    id = id,
    title = title,
    thumbnail = thumbnail,
    isFavorite = true
)