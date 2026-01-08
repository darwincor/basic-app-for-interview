package com.example.basicappforinterview.data.datasource

import com.example.basicappforinterview.data.local.FavoriteVideoEntity
import com.example.basicappforinterview.data.local.VideoDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val videoDao: VideoDao
) : LocalDataSource {
    override fun getFavoriteVideos(): Flow<List<FavoriteVideoEntity>> {
        return videoDao.getFavoriteVideos()
    }

    override suspend fun insertFavoriteVideo(video: FavoriteVideoEntity) {
        videoDao.insertFavoriteVideo(video)
    }

    override suspend fun deleteFavoriteVideo(video: FavoriteVideoEntity) {
        videoDao.deleteFavoriteVideo(video)
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return videoDao.isFavorite(id)
    }
}
