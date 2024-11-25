package com.example.basicappforinterview.presentation.videos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.presentation.common.VideoDetailsScreen
import com.example.basicappforinterview.presentation.videos.components.VideoItem

@Composable
fun VideosScreen(
    navController: NavController,
    viewModel: VideosViewMode = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val list = state.videos

    VideosContent(list) { video ->
        navController.navigate(VideoDetailsScreen(videoId = video.id))
    }
}

@Composable
private fun VideosContent(
    videos: List<Video>,
    onGoToDetails: (Video) -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
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