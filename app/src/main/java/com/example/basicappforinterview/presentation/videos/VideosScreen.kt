package com.example.basicappforinterview.presentation.videos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.presentation.videos.components.VideoItem

@Composable
fun VideosScreen(
    navController: NavController,
    viewModel: VideosViewMode = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val list = state.videos

    VideosContent(list) { video ->
        navController.navigate("videoDetails")
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