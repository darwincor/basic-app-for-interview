package com.example.basicappforinterview.presentation.videodetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.basicappforinterview.domain.model.VideoDetails
import com.example.basicappforinterview.domain.usecase.GetVideoDetails
import com.example.basicappforinterview.domain.util.AppError
import com.example.basicappforinterview.presentation.common.VideoDetailsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.basicappforinterview.domain.util.onError
import com.example.basicappforinterview.domain.util.onSuccess
import com.example.basicappforinterview.presentation.util.asUiText
import javax.inject.Inject

@HiltViewModel
class VideoDetailsViewModel @Inject constructor(
    private val getVideoDetails: GetVideoDetails,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = savedStateHandle.toRoute<VideoDetailsScreen>()

    private var hasLoadedInitialData = false
    private var _state = MutableStateFlow(VideoDetailsState(id = args.videoId))
    val state: StateFlow<VideoDetailsState> = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadVideoDetails(args.videoId.toString())
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            VideoDetailsState(id = args.videoId)
        )

    private fun loadVideoDetails(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            getVideoDetails.invoke(id)
                .onSuccess { videoDetails: VideoDetails ->
                    _state.value = _state.value.copy(
                        title = videoDetails.title,
                        description = videoDetails.description,
                        thumbnail = videoDetails.thumbnail,
                        backdrop = videoDetails.backdrop,
                        isLoading = false
                    )
                }
                .onError { error: AppError ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = error.asUiText()
                    )
                }
        }
    }
}