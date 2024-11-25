package com.example.basicappforinterview.presentation.videos

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VideosViewMode(): ViewModel() {

    private val _state = MutableStateFlow(VideosState())
    val state: StateFlow<VideosState> = _state

}