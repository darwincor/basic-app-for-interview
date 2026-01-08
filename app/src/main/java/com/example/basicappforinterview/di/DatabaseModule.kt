package com.example.basicappforinterview.di

import android.content.Context
import androidx.room.Room
import com.example.basicappforinterview.data.local.VideoDao
import com.example.basicappforinterview.data.local.VideoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideVideoDatabase(@ApplicationContext context: Context): VideoDatabase {
        return Room.databaseBuilder(
            context,
            VideoDatabase::class.java,
            VideoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideVideoDao(database: VideoDatabase): VideoDao {
        return database.videoDao
    }
}
