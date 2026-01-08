package com.example.basicappforinterview.domain.usecase

import com.example.basicappforinterview.domain.Repository
import com.example.basicappforinterview.domain.model.Video
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class ToggleFavoriteTest {

    private lateinit var repository: Repository
    private lateinit var toggleFavorite: ToggleFavorite

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        toggleFavorite = ToggleFavorite(repository)
    }

    @Test
    fun `invoke should insert video when not favorite`() = runTest {
        // Given
        val video = Video(id = 1, title = "Test Video", thumbnail = "thumb.jpg", isFavorite = false)
        coEvery { repository.isFavorite(video.id) } returns false

        // When
        toggleFavorite(video)

        // Then
        coVerify { repository.isFavorite(video.id) }
        coVerify { repository.insertFavoriteVideo(video) }
        coVerify(exactly = 0) { repository.deleteFavoriteVideo(any()) }
    }

    @Test
    fun `invoke should delete video when already favorite`() = runTest {
        // Given
        val video = Video(id = 2, title = "Favorite Video", thumbnail = "thumb.jpg", isFavorite = true)
        coEvery { repository.isFavorite(video.id) } returns true

        // When
        toggleFavorite(video)

        // Then
        coVerify { repository.isFavorite(video.id) }
        coVerify { repository.deleteFavoriteVideo(video) }
        coVerify(exactly = 0) { repository.insertFavoriteVideo(any()) }
    }

    @Test
    fun `invoke should check favorite status before toggling`() = runTest {
        // Given
        val video = Video(id = 3, title = "Another Video", thumbnail = "thumb.jpg")
        coEvery { repository.isFavorite(video.id) } returns false

        // When
        toggleFavorite(video)

        // Then
        coVerify(exactly = 1) { repository.isFavorite(video.id) }
    }

    @Test
    fun `invoke should handle multiple toggle operations`() = runTest {
        // Given
        val video = Video(id = 4, title = "Video", thumbnail = "thumb.jpg")

        // First toggle - not favorite, should insert
        coEvery { repository.isFavorite(video.id) } returns false
        toggleFavorite(video)

        // Second toggle - is favorite, should delete
        coEvery { repository.isFavorite(video.id) } returns true
        toggleFavorite(video)

        // Then
        coVerify(exactly = 2) { repository.isFavorite(video.id) }
        coVerify(exactly = 1) { repository.insertFavoriteVideo(video) }
        coVerify(exactly = 1) { repository.deleteFavoriteVideo(video) }
    }

    @Test
    fun `invoke should pass correct video object to repository`() = runTest {
        // Given
        val video = Video(
            id = 5,
            title = "Specific Video",
            thumbnail = "specific.jpg",
            isFavorite = false
        )
        coEvery { repository.isFavorite(video.id) } returns false

        // When
        toggleFavorite(video)

        // Then
        coVerify { repository.insertFavoriteVideo(video) }
    }

    @Test
    fun `invoke should propagate exceptions from repository isFavorite`() = runTest {
        // Given
        val video = Video(id = 6, title = "Error Video", thumbnail = "thumb.jpg")
        val exception = RuntimeException("Database error")
        coEvery { repository.isFavorite(video.id) } throws exception

        // When/Then
        val thrown = assertThrows(RuntimeException::class.java) {
            runBlocking { toggleFavorite(video) }
        }
        assertEquals("Database error", thrown.message)
    }

    @Test
    fun `invoke should propagate exceptions from repository insertFavoriteVideo`() = runTest {
        // Given
        val video = Video(id = 7, title = "Insert Error", thumbnail = "thumb.jpg")
        val exception = RuntimeException("Insert failed")
        coEvery { repository.isFavorite(video.id) } returns false
        coEvery { repository.insertFavoriteVideo(video) } throws exception

        // When/Then
        val thrown = assertThrows(RuntimeException::class.java) {
            runBlocking { toggleFavorite(video) }
        }
        assertEquals("Insert failed", thrown.message)
    }

    @Test
    fun `invoke should propagate exceptions from repository deleteFavoriteVideo`() = runTest {
        // Given
        val video = Video(id = 8, title = "Delete Error", thumbnail = "thumb.jpg")
        val exception = RuntimeException("Delete failed")
        coEvery { repository.isFavorite(video.id) } returns true
        coEvery { repository.deleteFavoriteVideo(video) } throws exception

        // When/Then
        val thrown = assertThrows(RuntimeException::class.java) {
            runBlocking { toggleFavorite(video) }
        }
        assertEquals("Delete failed", thrown.message)
    }
}

