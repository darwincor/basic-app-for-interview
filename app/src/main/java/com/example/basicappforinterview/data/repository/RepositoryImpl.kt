package com.example.basicappforinterview.data.repository

import com.example.basicappforinterview.data.datasource.LocalDataSource
import com.example.basicappforinterview.data.datasource.RemoteDataSource
import com.example.basicappforinterview.data.mapper.toDomain
import com.example.basicappforinterview.data.mapper.toEntity
import com.example.basicappforinterview.domain.Repository
import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.domain.model.VideoDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {
    override suspend fun getVideos(): List<Video> {
        return remoteDataSource.getVideos().map { it.toDomain() }
    }

    override suspend fun getVideoDetails(videoId: String): VideoDetails {
        return remoteDataSource.getVideoDetails(videoId).toDomain()
    }

    override fun getFavoriteVideos(): Flow<List<Video>> {
        return localDataSource.getFavoriteVideos().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insertFavoriteVideo(video: Video) {
        localDataSource.insertFavoriteVideo(video.toEntity())
    }

    override suspend fun deleteFavoriteVideo(video: Video) {
        localDataSource.deleteFavoriteVideo(video.toEntity())
    }

    override suspend fun isFavorite(videoId: Int): Boolean {
        return localDataSource.isFavorite(videoId)
    }
}