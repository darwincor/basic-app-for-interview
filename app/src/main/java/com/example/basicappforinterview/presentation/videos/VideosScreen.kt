package com.example.basicappforinterview.presentation.videos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import com.example.basicappforinterview.presentation.util.UiText
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

    VideosContent(
        isLoading = state.isLoading,
        videos = state.videos,
        favoriteVideos = state.favoriteVideos,
        error = state.error,
        onEvent = { viewModel.onEvent(it) },
        onGoToDetails = { video ->
            navController.navigate(VideoDetailsScreen(videoId = video.id))
        }
    )

}

@Composable
private fun VideosContent(
    isLoading: Boolean = false,
    videos: List<Video>,
    favoriteVideos: List<Video>,
    error: UiText? = null,
    onEvent: (VideosEvent) -> Unit = {},
    onGoToDetails: (Video) -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (error != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = error.asString(),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onEvent(VideosEvent.Retry) }) {
                    Text(text = "Retry")
                }
            }
        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Favorites Section (if any)
                if (favoriteVideos.isNotEmpty()) {
                    item {
                        Text(
                            text = stringResource(R.string.favorites),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                        )
                    }
                    item {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.height(100.dp)
                        ) {
                            items(
                                items = favoriteVideos,
                                key = { it.id }
                            ) { video ->
                                VideoItem(
                                    modifier = Modifier
                                        .width(280.dp)
                                        .animateItem(),
                                    video = video,
                                    isFavorite = video.isFavorite,
                                    onToggleFavorite = { onEvent(VideosEvent.ToggleFavorite(it)) },
                                    onClick = { onGoToDetails(video) }
                                )
                            }
                        }
                    }
                }

                // Gallery Section
                item {
                    Text(
                        text = stringResource(R.string.video_gallery),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                    )
                }

                items(
                    items = videos,
                    key = { it.id }
                ) { video ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .animateItem()
                    ) {
                        VideoItem(
                            video = video,
                            isFavorite = video.isFavorite,
                            onToggleFavorite = { onEvent(VideosEvent.ToggleFavorite(it)) },
                            onClick = { onGoToDetails(video) }
                        )
                    }
                }
            }
        }
    }
}