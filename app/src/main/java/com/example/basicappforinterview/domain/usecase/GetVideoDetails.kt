package com.example.basicappforinterview.domain.usecase

import com.example.basicappforinterview.domain.Repository
import javax.inject.Inject

class GetVideoDetails @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(videoId: String) = repository.getVideoDetails(videoId)
}