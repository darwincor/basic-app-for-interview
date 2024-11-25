package com.example.basicappforinterview.data.api

import com.example.basicappforinterview.data.model.video.VideoDto
import com.example.basicappforinterview.data.model.video.VideosDto
import com.example.basicappforinterview.data.model.videoDetails.VideoDetailsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("discover/movie")
    suspend fun getVideos(
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): Response<VideosDto>

    @GET("movie/{videoId}")
    suspend fun getVideoDetails(
        @Path("videoId") videoId: String,
        @Query("language") language: String = "en-US"
    ): Response<VideoDetailsDto>
}