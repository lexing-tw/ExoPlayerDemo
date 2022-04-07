package io.github.demo.player

import android.os.Bundle
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import io.github.demo.player.databinding.ActivityMainBinding

@OptIn(UnstableApi::class)
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var exoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        exoPlayer = ExoPlayer.Builder(this).build().apply {
            playWhenReady = true
        }
        binding.playerView.player = exoPlayer
        playSample()
    }

    private fun playSample() {
        val sampleVideo =
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val mediaItem = MediaItem.fromUri(sampleVideo)
        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.prepare()
    }

    override fun onDestroy() {
        exoPlayer?.release()
        exoPlayer = null
        super.onDestroy()
    }
}
