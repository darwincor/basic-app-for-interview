package com.example.basicappforinterview.domain.usecase

import com.example.basicappforinterview.domain.Repository
import com.example.basicappforinterview.domain.model.Video
import javax.inject.Inject

class ToggleFavorite @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(video: Video) {
        if (repository.isFavorite(video.id)) {
            repository.deleteFavoriteVideo(video)
        } else {
            repository.insertFavoriteVideo(video)
        }
    }
}
