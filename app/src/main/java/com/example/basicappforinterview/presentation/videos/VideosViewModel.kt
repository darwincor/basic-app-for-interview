package com.example.basicappforinterview.presentation.videos

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class VideosViewMode @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(VideosState())
    val state: StateFlow<VideosState> = _state

}