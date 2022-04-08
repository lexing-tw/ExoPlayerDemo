package io.github.demo.player

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.demo.player.model.CurrentState
import io.github.demo.player.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(@ApplicationContext context: Context): ViewModel() {
    private val sampleVideo =
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    private val exoPlayer: ExoPlayer by lazy { ExoPlayer.Builder(context).build() }

    private val _uiStateFlow: MutableStateFlow<UiState>
        get() = MutableStateFlow(UiState(exoPlayer, CurrentState(mediaUrl = sampleVideo)))
    val uiStateFlow = _uiStateFlow.asStateFlow()

    init {
        exoPlayer.playWhenReady = true
        setMedia(sampleVideo)
    }

    private fun setMedia(url: String) {
        exoPlayer.setMediaItem(MediaItem.fromUri(url))
        exoPlayer.prepare()
        updateState(UiState(exoPlayer, CurrentState(mediaUrl = url)))
    }

    private fun updateState(state: UiState) {
        viewModelScope.launch {
            _uiStateFlow.emit(state)
        }
    }

    override fun onCleared() {
        exoPlayer.release()
        super.onCleared()
    }
}
