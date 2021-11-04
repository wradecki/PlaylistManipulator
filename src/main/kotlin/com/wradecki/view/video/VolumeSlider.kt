package com.wradecki.view.video

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sun.jna.platform.win32.DBT
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
                    setVolume(1F)
                } else {
                    setVolume(it)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
        )
    }

}

fun setVolume(volume: Float) {
    playerState.mediaPlayerComponent.mediaPlayer().audio().setVolume((volume*100).toInt())
}