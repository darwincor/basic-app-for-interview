package com.example.basicappforinterview.domain.usecase

import com.example.basicappforinterview.domain.Repository
import com.example.basicappforinterview.domain.model.VideoDetails
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class GetVideoDetailsTest {

    private lateinit var repository: Repository
    private lateinit var getVideoDetails: GetVideoDetails

    @BeforeEach
    fun setup() {
        repository = mockk()
        getVideoDetails = GetVideoDetails(repository)
    }

    @Test
    fun `invoke should return video details for given video id`() = runTest {
        // Given
        val videoId = "123"
        val expectedDetails = VideoDetails(
            id = 123,
            title = "Test Video",
            description = "This is a test video",
            thumbnail = "thumb.jpg",
            backdrop = "backdrop.jpg"
        )
        coEvery { repository.getVideoDetails(videoId) } returns expectedDetails

        // When
        val result = getVideoDetails(videoId)

        // Then
        assertEquals(expectedDetails, result)
        coVerify { repository.getVideoDetails(videoId) }
    }

    @Test
    fun `invoke should call repository with correct video id`() = runTest {
        // Given
        val videoId = "456"
        val videoDetails = VideoDetails(id = 456, title = "Another Video")
        coEvery { repository.getVideoDetails(videoId) } returns videoDetails

        // When
        getVideoDetails(videoId)

        // Then
        coVerify(exactly = 1) { repository.getVideoDetails(videoId) }
    }

    @Test
    fun `invoke should handle empty video details`() = runTest {
        // Given
        val videoId = "789"
        val emptyDetails = VideoDetails()
        coEvery { repository.getVideoDetails(videoId) } returns emptyDetails

        // When
        val result = getVideoDetails(videoId)

        // Then
        assertEquals(0, result.id)
        assertEquals("", result.title)
        assertEquals("", result.description)
        coVerify { repository.getVideoDetails(videoId) }
    }

    @Test
    fun `invoke should propagate exceptions from repository`() = runTest {
        // Given
        val videoId = "error"
        val exception = RuntimeException("Network error")
        coEvery { repository.getVideoDetails(videoId) } throws exception

        // When/Then
        val thrown = assertThrows(RuntimeException::class.java) {
            runBlocking {
                getVideoDetails(videoId)
            }
        }
        assertEquals("Network error", thrown.message)
    }
}

