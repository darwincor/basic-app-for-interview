package com.example.basicappforinterview.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.basicappforinterview.presentation.common.VideoDetailsScreen
import com.example.basicappforinterview.presentation.common.VideosScreen
import com.example.basicappforinterview.presentation.videodetails.VideoDetailsScreen
import com.example.basicappforinterview.presentation.videos.VideosScreen
import com.example.basicappforinterview.ui.theme.BasicAppForInterviewTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicAppForInterviewTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    Scaffold(

    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = VideosScreen,
            modifier = modifier.padding(innerPadding)
        ) {
            composable<VideosScreen> {
                VideosScreen(navController = navController)
            }
            composable<VideoDetailsScreen> {
                VideoDetailsScreen(navController = navController)
            }
        }
    }
}


