package com.example.basicappforinterview.presentation.videos

import androidx.lifecycle.ViewModel
import com.example.basicappforinterview.domain.model.Video
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class VideosViewMode @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(VideosState())
    val state: StateFlow<VideosState> = _state

    init {
        //Fill state with mock data for now
        _state.value = _state.value.copy(
            videos = listOf(
                Video("1", "Video 1", "https://storage.googleapis.com/pod_public/750/151089.jpg"),
                Video("2", "Video 2", "https://storage.googleapis.com/pod_public/750/151089.jpg"),
                Video("3", "Video 3", "https://storage.googleapis.com/pod_public/750/151089.jpg"),
                Video("4", "Video 4", "https://storage.googleapis.com/pod_public/750/151089.jpg"),
            )
        )
    }

}