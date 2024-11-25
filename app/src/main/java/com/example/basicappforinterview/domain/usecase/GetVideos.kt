package com.example.basicappforinterview.domain.usecase

import com.example.basicappforinterview.domain.Repository
import javax.inject.Inject

class GetVideos @Inject constructor( private val repository: Repository) {
    suspend operator fun invoke() = repository.getVideos()
}