package com.example.basicappforinterview.data.repository

import com.example.basicappforinterview.domain.Repository
import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.domain.model.VideoDetail

class RepositoryImpl: Repository {
    override suspend fun getVideos(): List<Video> {
        //Return mocked data
        return listOf(
            Video(
                id = "1",
                title = "Video 1",
                thumbnail = "https://storage.googleapis.com/pod_public/750/151089.jpg"
            ),
            Video(
                id = "2",
                title = "Video 2",
                thumbnail = "https://storage.googleapis.com/pod_public/750/151089.jpg"
            ),
            Video(
                id = "3",
                title = "Video 3",
                thumbnail = "https://storage.googleapis.com/pod_public/750/151089.jpg"
            ),
        )
    }

    override suspend fun getVideoDetail(videoId: String): VideoDetail {
        //Return mocked data
        return VideoDetail(
            id = "1",
            title = "Video 1",
            description = "Description of video 1",
            thumbnail = "https://storage.googleapis.com/pod_public/750/151089.jpg",
            duration = "10:00"
        )
    }
}