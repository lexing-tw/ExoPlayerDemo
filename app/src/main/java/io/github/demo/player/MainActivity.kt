package io.github.demo.player

import android.os.Bundle
import android.view.SurfaceView
import androidx.activity.viewModels
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import io.github.demo.player.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
@OptIn(UnstableApi::class)
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val playerViewModel: PlayerViewModel by viewModels()

    private var currentOutputView: SurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launchWhenCreated {
            playerViewModel.uiStateFlow.collectLatest {
                binding.playerControlView.player = it.player
                initSurfaceViews(it.player)
                setCurrentOutput(it.player, binding.primaryPlayerView)
            }
        }
    }

    private fun initSurfaceViews(player: ExoPlayer) {
        binding.primaryPlayerView.setOnClickListener {
            setCurrentOutput(player, binding.primaryPlayerView)
        }
        binding.secondaryPlayerView.setOnClickListener {
            setCurrentOutput(player, binding.secondaryPlayerView)
        }
    }

    private fun setCurrentOutput(player: ExoPlayer, surfaceView: SurfaceView) {
        if (surfaceView != currentOutputView) {
            player.clearVideoSurfaceView(currentOutputView)
            currentOutputView = surfaceView
            player.setVideoSurfaceView(surfaceView)
        }
    }
}
