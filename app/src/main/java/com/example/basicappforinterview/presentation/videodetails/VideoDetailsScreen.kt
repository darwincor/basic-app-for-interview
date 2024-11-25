package com.example.basicappforinterview.presentation.videodetails

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController

@Composable
fun VideoDetailsScreen(
    navController: NavController
) {
    Box (
        contentAlignment = Alignment.Center
    ){
        Text(text = "Screen of Video Details")
    }
}