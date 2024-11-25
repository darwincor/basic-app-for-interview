package com.example.basicappforinterview.presentation.videos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.presentation.common.VideoDetailsScreen
import com.example.basicappforinterview.presentation.videos.components.VideoItem

@Composable
fun VideosScreen(
    navController: NavController,
    viewModel: VideosViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val list = state.videos
    val isLoading = state.isLoading

    VideosContent(isLoading = isLoading, videos = list) { video ->
        navController.navigate(VideoDetailsScreen(videoId = video.id))
    }
}

@Composable
private fun VideosContent(
    isLoading: Boolean = false,
    videos: List<Video>,
    onGoToDetails: (Video) -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(videos) { video ->
                    VideoItem(
                        video = video,
                        onClick = {
                            onGoToDetails(video)
                        }
                    )
                }
            }
        }
    }
}