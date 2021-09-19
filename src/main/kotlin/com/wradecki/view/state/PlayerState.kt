package com.wradecki.view.state

import androidx.compose.runtime.MutableState
import java.awt.Component

data class PlayerState(
    var mediaPlayerComponent: Component,
    var playerUrl: MutableState<String>,
    var playerHeight: MutableState<Int>,
    var isPlaying: MutableState<Boolean>,
    var isFullScreen: MutableState<Boolean>,
    var videoTime: MutableState<Float>,
    var isSeekable: MutableState<Boolean>,
)
