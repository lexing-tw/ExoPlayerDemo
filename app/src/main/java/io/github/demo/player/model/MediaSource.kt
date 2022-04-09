package io.github.demo.player.model

import androidx.media3.common.MimeTypes

private val mp4Video =
    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
private val mpdVideo =
    "https://storage.googleapis.com/wvmedia/clear/h264/tears/tears_uhd.mpd"

val mediaItems = mapOf(
    mp4Video to MimeTypes.APPLICATION_MP4,
    mpdVideo to MimeTypes.APPLICATION_MPD
)
