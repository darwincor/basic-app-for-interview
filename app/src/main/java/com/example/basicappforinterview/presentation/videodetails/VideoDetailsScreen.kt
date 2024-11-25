package com.example.basicappforinterview.presentation.videodetails

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun VideoDetailsScreen(
    navController: NavController,
    viewModel: VideoDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    VideoDetailsContent(state.id)
}

@Composable
fun VideoDetailsContent(id: String) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(text = "The id is $id")
    }
}