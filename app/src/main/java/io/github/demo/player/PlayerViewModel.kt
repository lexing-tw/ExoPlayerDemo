package io.github.demo.player

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.demo.player.model.CurrentState
import io.github.demo.player.model.UiState
import io.github.demo.player.model.mediaItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(@ApplicationContext context: Context) : ViewModel(),
    Player.Listener {
    private val exoPlayer: ExoPlayer by lazy { ExoPlayer.Builder(context).build() }

    private val _uiStateFlow: MutableStateFlow<UiState>
        get() = MutableStateFlow(UiState(exoPlayer, CurrentState(mediaUrl = "")))
    val uiStateFlow = _uiStateFlow.asStateFlow()

    init {
        exoPlayer.playWhenReady = true
        exoPlayer.addListener(this)
        setMedia(mediaItems)
    }

    private fun setMedia(items: Map<String, String>) {
        items.forEach { entry ->
            exoPlayer.addMediaItem(
                MediaItem.Builder()
                    .setMediaId(entry.key)
                    .setUri(entry.key)
                    .setMimeType(entry.value)
                    .build()
            )
        }
        exoPlayer.prepare()
        updateState(UiState(exoPlayer, CurrentState(mediaUrl = "")))
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        Log.i("TAG", "play state $playbackState")
        super.onPlaybackStateChanged(playbackState)
    }

    private fun updateState(state: UiState) {
        viewModelScope.launch {
            _uiStateFlow.emit(state)
        }
    }

    override fun onCleared() {
        exoPlayer.removeListener(this)
        exoPlayer.release()
        super.onCleared()
    }
}
