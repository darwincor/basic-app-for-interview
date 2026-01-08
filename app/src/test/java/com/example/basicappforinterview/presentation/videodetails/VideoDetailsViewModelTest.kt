package com.example.basicappforinterview.presentation.videodetails

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import com.example.basicappforinterview.domain.model.VideoDetails
import com.example.basicappforinterview.domain.usecase.GetVideoDetails
import com.example.basicappforinterview.presentation.common.VideoDetailsScreen
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VideoDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getVideoDetails: GetVideoDetails
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: VideoDetailsViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getVideoDetails = mockk()
        savedStateHandle = mockk()

        // Mock the extension function toRoute
        mockkStatic("androidx.navigation.SavedStateHandleKt")
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()

        // Unmock the extension function toRoute
        unmockkStatic("androidx.navigation.SavedStateHandleKt")
    }

    @Test
    fun `initial state should contain video id from saved state`() = runTest {
        // Given
        val videoId = 123
        val videoDetails = VideoDetails(id = videoId, title = "Test Video")
        every { savedStateHandle.toRoute<VideoDetailsScreen>() } returns VideoDetailsScreen(videoId)
        coEvery { getVideoDetails.invoke(videoId.toString()) } returns videoDetails

        // When
        viewModel = VideoDetailsViewModel(getVideoDetails, savedStateHandle)

        // Then
        viewModel.state.test {
            val initialState = awaitItem()
            assertEquals(videoId, initialState.id)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `state should load video details on initialization`() = runTest {
        // Given
        val videoId = 456
        val videoDetails = VideoDetails(
            id = videoId,
            title = "Amazing Video",
            description = "This is an amazing video",
            thumbnail = "thumb.jpg",
            backdrop = "backdrop.jpg"
        )
        every { savedStateHandle.toRoute<VideoDetailsScreen>() } returns VideoDetailsScreen(videoId)
        coEvery { getVideoDetails.invoke(videoId.toString()) } returns videoDetails

        // When
        viewModel = VideoDetailsViewModel(getVideoDetails, savedStateHandle)

        // Then
        viewModel.state.test {
            val initialState = awaitItem()
            assertEquals(videoId, initialState.id)
            assertTrue(initialState.isLoading)

            testDispatcher.scheduler.advanceUntilIdle()

            val loadedState = awaitItem()
            assertEquals("Amazing Video", loadedState.title)
            assertEquals("This is an amazing video", loadedState.description)
            assertEquals("thumb.jpg", loadedState.thumbnail)
            assertEquals("backdrop.jpg", loadedState.backdrop)
            assertFalse(loadedState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should set isLoading to true while fetching video details`() = runTest {
        // Given
        val videoId = 789
        val videoDetails = VideoDetails(id = videoId, title = "Loading Test")
        every { savedStateHandle.toRoute<VideoDetailsScreen>() } returns VideoDetailsScreen(videoId)
        coEvery { getVideoDetails.invoke(videoId.toString()) } returns videoDetails

        // When
        viewModel = VideoDetailsViewModel(getVideoDetails, savedStateHandle)

        // Then
        viewModel.state.test {
            val initialState = awaitItem()
            assertTrue(initialState.isLoading)

            testDispatcher.scheduler.advanceUntilIdle()

            val loadedState = awaitItem()
            assertFalse(loadedState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should only load data once on initialization`() = runTest {
        // Given
        val videoId = 999
        val videoDetails = VideoDetails(id = videoId, title = "Single Load Test")
        every { savedStateHandle.toRoute<VideoDetailsScreen>() } returns VideoDetailsScreen(videoId)
        coEvery { getVideoDetails.invoke(videoId.toString()) } returns videoDetails

        // When
        viewModel = VideoDetailsViewModel(getVideoDetails, savedStateHandle)

        // Collect state multiple times
        viewModel.state.test {
            awaitItem()
            testDispatcher.scheduler.advanceUntilIdle()
            awaitItem()
            cancelAndIgnoreRemainingEvents()
        }

        // Then - should only be called once
        coVerify(exactly = 1) { getVideoDetails.invoke(videoId.toString()) }
    }

    @Test
    fun `should handle empty video details`() = runTest {
        // Given
        val videoId = 111
        val emptyVideoDetails = VideoDetails()
        every { savedStateHandle.toRoute<VideoDetailsScreen>() } returns VideoDetailsScreen(videoId)
        coEvery { getVideoDetails.invoke(videoId.toString()) } returns emptyVideoDetails

        // When
        viewModel = VideoDetailsViewModel(getVideoDetails, savedStateHandle)

        // Then
        viewModel.state.test {
            awaitItem() // Initial state
            testDispatcher.scheduler.advanceUntilIdle()

            val loadedState = awaitItem()
            assertEquals("", loadedState.title)
            assertEquals("", loadedState.description)
            assertEquals("", loadedState.thumbnail)
            assertEquals("", loadedState.backdrop)
            assertFalse(loadedState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }
}

