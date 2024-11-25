package com.example.basicappforinterview.data.repository

import com.example.basicappforinterview.data.datasource.RemoteDataSource
import com.example.basicappforinterview.data.mapper.toDomain
import com.example.basicappforinterview.domain.Repository
import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.domain.model.VideoDetails
import kotlinx.coroutines.delay
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataSource: RemoteDataSource
) : Repository {
    override suspend fun getVideos(): List<Video> {
        return dataSource.getVideos().map { it.toDomain() }
    }

    override suspend fun getVideoDetails(videoId: String): VideoDetails {
        return dataSource.getVideoDetails(videoId).toDomain()
    }
}