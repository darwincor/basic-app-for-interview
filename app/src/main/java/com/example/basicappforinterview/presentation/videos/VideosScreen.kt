package com.example.basicappforinterview.presentation.videos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.basicappforinterview.R
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
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.video_gallery),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
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