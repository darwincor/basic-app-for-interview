package com.example.basicappforinterview.data.datasource

import android.app.Application
import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.domain.model.VideoDetail
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val app: Application
): RemoteDataSource {
    override suspend fun getVideos(): List<Video> {
        TODO("Not yet implemented")
    }

    override suspend fun getVideoDetails(videoId: String): VideoDetail {
        TODO("Not yet implemented")
    }
}