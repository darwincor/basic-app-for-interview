package com.example.basicappforinterview.presentation.videos

import app.cash.turbine.test
import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.domain.usecase.GetFavoriteVideos
import com.example.basicappforinterview.domain.usecase.GetVideos
import com.example.basicappforinterview.domain.usecase.ToggleFavorite
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VideosViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getVideos: GetVideos
    private lateinit var getFavoriteVideos: GetFavoriteVideos
    private lateinit var toggleFavorite: ToggleFavorite
    private lateinit var viewModel: VideosViewModel

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getVideos = mockk()
        getFavoriteVideos = mockk()
        toggleFavorite = mockk(relaxed = true)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be empty`() = runTest {
        // Given
        coEvery { getVideos() } returns emptyList()
        every { getFavoriteVideos() } returns flowOf(emptyList())

        // When
        viewModel = VideosViewModel(getVideos, getFavoriteVideos, toggleFavorite)

        // Then
        viewModel.state.test {
            val initialState = awaitItem()
            assertTrue(initialState.videos.isEmpty())
            assertTrue(initialState.favoriteVideos.isEmpty())
            assertTrue(initialState.isLoading)
            assertEquals("", initialState.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should load videos on initialization`() = runTest {
        // Given
        val videos = listOf(
            Video(id = 1, title = "Video 1", thumbnail = "thumb1.jpg"),
            Video(id = 2, title = "Video 2", thumbnail = "thumb2.jpg")
        )
        coEvery { getVideos() } returns videos
        every { getFavoriteVideos() } returns flowOf(emptyList())

        // When
        viewModel = VideosViewModel(getVideos, getFavoriteVideos, toggleFavorite)

        // Then
        viewModel.state.test {
            val initialState = awaitItem()
            assertTrue(initialState.isLoading)

            testDispatcher.scheduler.advanceUntilIdle()

            val loadedState = awaitItem()
            assertEquals(2, loadedState.videos.size)
            assertFalse(loadedState.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should mark videos as favorite when in favorites list`() = runTest {
        // Given
        val videos = listOf(
            Video(id = 1, title = "Video 1", thumbnail = "thumb1.jpg"),
            Video(id = 2, title = "Video 2", thumbnail = "thumb2.jpg"),
            Video(id = 3, title = "Video 3", thumbnail = "thumb3.jpg")
        )
        val favorites = listOf(
            Video(id = 2, title = "Video 2", thumbnail = "thumb2.jpg", isFavorite = true)
        )
        coEvery { getVideos() } returns videos
        every { getFavoriteVideos() } returns flowOf(favorites)

        // When
        viewModel = VideosViewModel(getVideos, getFavoriteVideos, toggleFavorite)

        // Then
        viewModel.state.test {
            awaitItem() // Initial state

            testDispatcher.scheduler.advanceUntilIdle()

            val loadedState = awaitItem()
            assertFalse(loadedState.videos[0].isFavorite)
            assertTrue(loadedState.videos[1].isFavorite)
            assertFalse(loadedState.videos[2].isFavorite)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should update favorite videos when favorites change`() = runTest {
        // Given
        val videos = listOf(
            Video(id = 1, title = "Video 1", thumbnail = "thumb1.jpg"),
            Video(id = 2, title = "Video 2", thumbnail = "thumb2.jpg")
        )

        // Use a channel or StateFlow to control emissions
        every { getFavoriteVideos() } returns flow {
            emit(emptyList())
            delay(100) // Small delay before second emission
            emit(
                listOf(
                    Video(id = 1, title = "Video 1", thumbnail = "thumb1.jpg", isFavorite = true)
                )
            )
        }

        coEvery { getVideos() } returns videos

        // When
        viewModel = VideosViewModel(getVideos, getFavoriteVideos, toggleFavorite)

        // Then
        viewModel.state.test {
            awaitItem() // Initial loading state

            testDispatcher.scheduler.advanceUntilIdle()

            val firstLoadedState = awaitItem()
            assertFalse(firstLoadedState.videos[0].isFavorite)

            testDispatcher.scheduler.advanceTimeBy(100)

            val updatedState = awaitItem()
            assertTrue(updatedState.videos[0].isFavorite)
            assertEquals(1, updatedState.favoriteVideos.size)

            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `onEvent ToggleFavorite should call toggle favorite use case`() = runTest {
        // Given
        val video = Video(id = 1, title = "Test Video", thumbnail = "thumb.jpg")
        coEvery { getVideos() } returns listOf(video)
        every { getFavoriteVideos() } returns flowOf(emptyList())
        viewModel = VideosViewModel(getVideos, getFavoriteVideos, toggleFavorite)

        // When
        viewModel.onEvent(VideosEvent.ToggleFavorite(video))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { toggleFavorite(video) }
    }

    @Test
    fun `should handle error when loading videos fails`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { getVideos() } throws RuntimeException(errorMessage)
        every { getFavoriteVideos() } returns flowOf(emptyList())

        // When
        viewModel = VideosViewModel(getVideos, getFavoriteVideos, toggleFavorite)

        // Then
        viewModel.state.test {
            awaitItem() // Initial state with loading true

            testDispatcher.scheduler.advanceUntilIdle()

            val errorState = awaitItem()
            assertEquals(errorMessage, errorState.error)
            assertFalse(errorState.isLoading)
            assertTrue(errorState.videos.isEmpty())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should only load data once on initialization`() = runTest {
        // Given
        val videos = listOf(Video(id = 1, title = "Video 1", thumbnail = "thumb1.jpg"))
        coEvery { getVideos() } returns videos
        every { getFavoriteVideos() } returns flowOf(emptyList())

        // When
        viewModel = VideosViewModel(getVideos, getFavoriteVideos, toggleFavorite)

        // Collect state multiple times
        viewModel.state.test {
            awaitItem()
            testDispatcher.scheduler.advanceUntilIdle()
            awaitItem()
            cancelAndIgnoreRemainingEvents()
        }

        // Then - should only be called once
        coVerify(exactly = 1) { getVideos() }
    }

    @Test
    fun `should handle empty videos list`() = runTest {
        // Given
        coEvery { getVideos() } returns emptyList()
        every { getFavoriteVideos() } returns flowOf(emptyList())

        // When
        viewModel = VideosViewModel(getVideos, getFavoriteVideos, toggleFavorite)

        // Then
        viewModel.state.test {
            awaitItem() // Initial state

            testDispatcher.scheduler.advanceUntilIdle()

            val loadedState = awaitItem()
            assertTrue(loadedState.videos.isEmpty())
            assertFalse(loadedState.isLoading)
            assertEquals("", loadedState.error)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should update videos list with favorite status when both lists are loaded`() = runTest {
        // Given
        val videos = listOf(
            Video(id = 1, title = "Video 1", thumbnail = "thumb1.jpg"),
            Video(id = 2, title = "Video 2", thumbnail = "thumb2.jpg"),
            Video(id = 3, title = "Video 3", thumbnail = "thumb3.jpg"),
            Video(id = 4, title = "Video 4", thumbnail = "thumb4.jpg")
        )
        val favorites = listOf(
            Video(id = 1, title = "Video 1", thumbnail = "thumb1.jpg", isFavorite = true),
            Video(id = 3, title = "Video 3", thumbnail = "thumb3.jpg", isFavorite = true)
        )
        coEvery { getVideos() } returns videos
        every { getFavoriteVideos() } returns flowOf(favorites)

        // When
        viewModel = VideosViewModel(getVideos, getFavoriteVideos, toggleFavorite)

        // Then
        viewModel.state.test {
            awaitItem() // Initial state

            testDispatcher.scheduler.advanceUntilIdle()

            val loadedState = awaitItem()
            assertEquals(4, loadedState.videos.size)
            assertEquals(2, loadedState.favoriteVideos.size)
            assertTrue(loadedState.videos[0].isFavorite) // Video 1
            assertFalse(loadedState.videos[1].isFavorite) // Video 2
            assertTrue(loadedState.videos[2].isFavorite) // Video 3
            assertFalse(loadedState.videos[3].isFavorite) // Video 4

            cancelAndIgnoreRemainingEvents()
        }
    }
}