package com.example.basicappforinterview.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteVideoEntity::class], version = 1)
abstract class VideoDatabase : RoomDatabase() {
    abstract val videoDao: VideoDao

    companion object {
        const val DATABASE_NAME = "video_db"
    }
}
