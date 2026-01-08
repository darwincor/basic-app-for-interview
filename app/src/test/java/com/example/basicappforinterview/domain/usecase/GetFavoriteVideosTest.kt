package com.example.basicappforinterview.domain.usecase

import com.example.basicappforinterview.domain.Repository
import com.example.basicappforinterview.domain.model.Video
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class GetFavoriteVideosTest {

    private lateinit var repository: Repository
    private lateinit var getFavoriteVideos: GetFavoriteVideos

    @BeforeEach
    fun setup() {
        repository = mockk()
        getFavoriteVideos = GetFavoriteVideos(repository)
    }

    @Test
    fun `invoke should return flow of favorite videos from repository`() = runTest {
        // Given
        val favoriteVideos = listOf(
            Video(id = 1, title = "Video 1", thumbnail = "thumb1.jpg", isFavorite = true),
            Video(id = 2, title = "Video 2", thumbnail = "thumb2.jpg", isFavorite = true)
        )
        every { repository.getFavoriteVideos() } returns flowOf(favoriteVideos)

        // When
        val result = getFavoriteVideos().first()

        // Then
        assertEquals(favoriteVideos, result)
        verify {
            @Suppress("UnusedFlow")
            repository.getFavoriteVideos()
        }
    }

    @Test
    fun `invoke should return empty flow when no favorites exist`() = runTest {
        // Given
        every { repository.getFavoriteVideos() } returns flowOf(emptyList())

        // When
        val result = getFavoriteVideos().first()

        // Then
        assertTrue(result.isEmpty())
        verify {
            @Suppress("UnusedFlow")
            repository.getFavoriteVideos()
        }
    }

    @Test
    fun `invoke should return flow that emits multiple values`() = runTest {
        // Given
        val firstList = listOf(
            Video(id = 1, title = "Video 1", thumbnail = "thumb1.jpg", isFavorite = true)
        )
        val secondList = listOf(
            Video(id = 1, title = "Video 1", thumbnail = "thumb1.jpg", isFavorite = true),
            Video(id = 2, title = "Video 2", thumbnail = "thumb2.jpg", isFavorite = true)
        )
        every { repository.getFavoriteVideos() } returns flowOf(firstList, secondList)

        // When
        val results = mutableListOf<List<Video>>()
        getFavoriteVideos().collect { results.add(it) }

        // Then
        assertEquals(2, results.size)
        assertEquals(firstList, results[0])
        assertEquals(secondList, results[1])
        verify {
            @Suppress("UnusedFlow")
            repository.getFavoriteVideos()
        }
    }
}

