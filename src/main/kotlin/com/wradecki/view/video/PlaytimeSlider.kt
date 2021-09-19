package com.wradecki.view.video

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wradecki.view.playerState

@Composable
fun PlaytimeSlider() {
    if (playerState.isSeekable.value) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 100.dp)
        ) {
            Slider(
                value = playerState.videoTime.value,
                onValueChange = {
                    playerState.videoTime.value = it
                    if (it > 0.99F) {
                        playerState.mediaPlayerComponent.mediaPlayer().controls().setPosition(0.99F)
                    } else {
                        playerState.mediaPlayerComponent.mediaPlayer().controls().setPosition(it)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}