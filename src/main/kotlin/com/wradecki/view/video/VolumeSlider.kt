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
fun VolumeSlider() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Slider(
            value = playerState.volume.value,
            onValueChange = {
                playerState.volume.value = it
                if (it > 1F) {
                    playerState.mediaPlayerComponent.mediaPlayer().audio().setVolume(100)
                } else {
                    playerState.mediaPlayerComponent.mediaPlayer().audio().setVolume((it*100).toInt())
                }
            },
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
        )
    }

}