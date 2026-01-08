package com.example.basicappforinterview.domain.usecase

import com.example.basicappforinterview.domain.Repository
import com.example.basicappforinterview.domain.model.Video
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetVideosTest {

    private lateinit var repository: Repository
    private lateinit var getVideos: GetVideos

    @BeforeEach
    fun setup() {
        repository = mockk()
        getVideos = GetVideos(repository)
    }

    @Test
    fun `invoke should return list of videos from repository`() = runTest {
        // Given
        val expectedVideos = listOf(
            Video(id = 1, title = "Video 1", thumbnail = "thumb1.jpg"),
            Video(id = 2, title = "Video 2", thumbnail = "thumb2.jpg"),
            Video(id = 3, title = "Video 3", thumbnail = "thumb3.jpg")
        )
        coEvery { repository.getVideos() } returns expectedVideos

        // When
        val result = getVideos()

        // Then
        assertEquals(expectedVideos, result)
        assertEquals(3, result.size)
        coVerify { repository.getVideos() }
    }

    @Test
    fun `invoke should return empty list when no videos exist`() = runTest {
        // Given
        coEvery { repository.getVideos() } returns emptyList()

        // When
        val result = getVideos()

        // Then
        assertTrue(result.isEmpty())
        coVerify { repository.getVideos() }
    }

    @Test
    fun `invoke should call repository exactly once`() = runTest {
        // Given
        val videos = listOf(Video(id = 1, title = "Test", thumbnail = "thumb.jpg"))
        coEvery { repository.getVideos() } returns videos

        // When
        getVideos()

        // Then
        coVerify(exactly = 1) { repository.getVideos() }
    }

    @Test
    fun `invoke should return videos with correct properties`() = runTest {
        // Given
        val video = Video(
            id = 42,
            title = "Sample Video",
            thumbnail = "sample.jpg",
            isFavorite = true
        )
        coEvery { repository.getVideos() } returns listOf(video)

        // When
        val result = getVideos()

        // Then
        assertEquals(1, result.size)
        val returnedVideo = result.first()
        assertEquals(42, returnedVideo.id)
        assertEquals("Sample Video", returnedVideo.title)
        assertEquals("sample.jpg", returnedVideo.thumbnail)
        assertTrue(returnedVideo.isFavorite)
    }

    @Test
    fun `invoke should propagate exceptions from repository`() = runTest {
        // Given
        val exception = RuntimeException("Failed to fetch videos")
        coEvery { repository.getVideos() } throws exception

        // When/Then
        val thrown = assertThrows(RuntimeException::class.java) {
            runBlocking { getVideos() }
        }
        assertEquals("Failed to fetch videos", thrown.message)
    }
}

