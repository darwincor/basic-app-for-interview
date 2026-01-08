package com.example.basicappforinterview.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {
    @Query("SELECT * FROM favorite_videos")
    fun getFavoriteVideos(): Flow<List<FavoriteVideoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteVideo(video: FavoriteVideoEntity)

    @Delete
    suspend fun deleteFavoriteVideo(video: FavoriteVideoEntity)

    @Query("SELECT EXISTS(SELECT * FROM favorite_videos WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}
