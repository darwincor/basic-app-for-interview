package com.example.basicappforinterview.presentation.videos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicappforinterview.domain.model.Video
import com.example.basicappforinterview.domain.usecase.GetFavoriteVideos
import com.example.basicappforinterview.domain.usecase.GetVideos
import com.example.basicappforinterview.domain.usecase.ToggleFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideosViewModel @Inject constructor(
    private val getVideosUseCase: GetVideos,
    private val getFavoriteVideosUseCase: GetFavoriteVideos,
    private val toggleFavoriteUseCase: ToggleFavorite
) : ViewModel() {

    private val _state = MutableStateFlow(VideosState())
    val state: StateFlow<VideosState> = _state.asStateFlow()

    init {
        loadVideos()
        observeFavorites()
    }

    fun onEvent(event: VideosEvent) {
        when (event) {
            is VideosEvent.ToggleFavorite -> {
                viewModelScope.launch {
                    toggleFavoriteUseCase(event.video)
                }
            }
        }
    }

    private fun loadVideos() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val videos = getVideosUseCase()
                _state.update { currentState ->
                    val favoriteIds = currentState.favoriteVideos.map { it.id }.toSet()
                    currentState.copy(
                        videos = videos.map { it.copy(isFavorite = favoriteIds.contains(it.id)) },
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
            }
        }
    }

    private fun observeFavorites() {
        getFavoriteVideosUseCase()
            .onEach { favorites ->
                _state.update { currentState ->
                    val favoriteIds = favorites.map { it.id }.toSet()
                    currentState.copy(
                        favoriteVideos = favorites,
                        videos = currentState.videos.map {
                            it.copy(
                                isFavorite = favoriteIds.contains(
                                    it.id
                                )
                            )
                        }
                    )
                }
            }.launchIn(viewModelScope)
    }
}