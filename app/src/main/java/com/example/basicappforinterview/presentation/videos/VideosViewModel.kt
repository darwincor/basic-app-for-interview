package com.example.basicappforinterview.presentation.videos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicappforinterview.domain.usecase.GetVideos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideosViewModel @Inject constructor(
    private val getVideos: GetVideos
): ViewModel() {

    private val _state = MutableStateFlow(VideosState())
    val state: StateFlow<VideosState> = _state

    init {
        getVideos()
    }

    private fun getVideos() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )
            _state.value = _state.value.copy(
                videos = getVideos.invoke(),
                isLoading = false
            )
        }
    }
}