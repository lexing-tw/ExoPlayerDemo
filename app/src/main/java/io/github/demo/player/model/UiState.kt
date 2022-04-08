package io.github.demo.player.model

import androidx.media3.exoplayer.ExoPlayer

data class UiState(
    val player: ExoPlayer,
    val currentState: CurrentState
)

data class CurrentState(
    val mediaUrl: String = "",
    val playbackPosition: Long = 0
)
