package com.example.basicappforinterview.presentation.videodetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun VideoDetailsScreen(
    navController: NavController,
    viewModel: VideoDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    VideoDetailsContent(isLoading = state.isLoading, id = state.id)
}

@Composable
fun VideoDetailsContent(
    isLoading: Boolean = false,
    id: Int
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        }
        else {
            Text(text = "The id is $id")
        }
    }
}