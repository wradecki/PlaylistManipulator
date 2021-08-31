package com.wradecki.view.video

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wradecki.view.playerHeight
import com.wradecki.view.playerUrl

@Composable
fun PlayerPanel() {
    if (playerUrl.value.isNotBlank()) {
        Row(modifier = Modifier.fillMaxWidth().height(playerHeight.value.dp)) {
            VideoPlayer(playerUrl.value, modifier = Modifier.fillMaxSize())
        }
        PlaytimeSlider()
        VideoPlayerButtons()
    }
}

