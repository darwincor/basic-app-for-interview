package com.example.basicappforinterview.presentation.videodetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.basicappforinterview.domain.usecase.GetVideoDetails
import com.example.basicappforinterview.presentation.common.VideoDetailsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoDetailsViewModel @Inject constructor(
    private val getVideoDetails: GetVideoDetails,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = savedStateHandle.toRoute<VideoDetailsScreen>()

    private var _state = MutableStateFlow(VideoDetailsState(id = args.videoId))
    val state: StateFlow<VideoDetailsState> = _state

    init {
        getVideoDetails(args.videoId.toString())
    }

    private fun getVideoDetails(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )
            val videoDetails = getVideoDetails.invoke(id)
            _state.value = _state.value.copy(
                title = videoDetails.title,
                description = videoDetails.description,
                thumbnail = videoDetails.thumbnail,
                backdrop = videoDetails.backdrop,
                isLoading = false
            )
        }
    }
}