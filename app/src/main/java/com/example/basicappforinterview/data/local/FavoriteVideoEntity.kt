package com.example.basicappforinterview.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_videos")
data class FavoriteVideoEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val thumbnail: String
)
