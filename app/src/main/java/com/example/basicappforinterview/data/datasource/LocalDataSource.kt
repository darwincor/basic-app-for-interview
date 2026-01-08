package com.example.basicappforinterview.data.datasource

import com.example.basicappforinterview.data.local.FavoriteVideoEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getFavoriteVideos(): Flow<List<FavoriteVideoEntity>>
    suspend fun insertFavoriteVideo(video: FavoriteVideoEntity)
    suspend fun deleteFavoriteVideo(video: FavoriteVideoEntity)
    suspend fun isFavorite(id: Int): Boolean
}
