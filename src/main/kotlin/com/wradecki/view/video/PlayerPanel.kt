package com.wradecki.view.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wradecki.view.playerState

@Composable
fun PlayerPanel() {
    if (playerState.playerUrl.value.isNotBlank()) {
        Row(modifier = Modifier.fillMaxWidth().height(playerState.playerHeight.value.dp)) {
            VideoPlayer(playerState.playerUrl.value, modifier = Modifier.fillMaxSize())
        }
        PlaytimeSlider()
        ControlsPanel()
    }
}

