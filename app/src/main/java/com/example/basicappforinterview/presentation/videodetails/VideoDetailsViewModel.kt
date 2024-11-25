package com.example.basicappforinterview.presentation.videodetails

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class VideoDetailsViewModel: ViewModel() {

    var _state = MutableStateFlow(VideoDetailsState())

}