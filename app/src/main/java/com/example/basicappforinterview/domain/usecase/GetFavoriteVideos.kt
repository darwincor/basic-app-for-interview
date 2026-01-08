package com.example.basicappforinterview.domain.usecase

import com.example.basicappforinterview.domain.Repository
import javax.inject.Inject

class GetFavoriteVideos @Inject constructor(private val repository: Repository) {
    operator fun invoke() = repository.getFavoriteVideos()
}
