package com.example.basicappforinterview.presentation.videodetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage

@Composable
fun VideoDetailsScreen(
    navController: NavController,
    viewModel: VideoDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    VideoDetailsContent(
        isLoading = state.isLoading,
        thumbnail = state.thumbnail,
        backdrop = state.backdrop,
        title = state.title,
        description = state.description
    )

}

@Composable
fun VideoDetailsContent(
    isLoading: Boolean = false,
    thumbnail: String = "",
    backdrop: String = "",
    title: String = "",
    description: String = "",
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                Box(
                    contentAlignment = Alignment.BottomStart
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth(),
                        model = backdrop,
                        contentDescription = "Thumbnail",
                        clipToBounds = true,
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Transparent,
                                        Color.Black
                                    )
                                )
                            )
                    )

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.size(16.dp))
                Row {
                    AsyncImage(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp)
                            .width(100.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        model = thumbnail,
                        contentDescription = "Thumbnail",
                        clipToBounds = true,
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = description,
                        color = Color.White
                    )
                }
            }
        }
    }
}