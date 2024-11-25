package com.example.basicappforinterview.presentation.videodetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.basicappforinterview.presentation.common.VideoDetailsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class VideoDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = savedStateHandle.toRoute<VideoDetailsScreen>()

    private var _state = MutableStateFlow(VideoDetailsState(id = args.videoId))
    val state: StateFlow<VideoDetailsState> = _state

}