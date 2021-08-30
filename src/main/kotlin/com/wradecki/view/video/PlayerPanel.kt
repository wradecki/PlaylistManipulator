package com.wradecki.view.video

import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wradecki.view.*

@Composable
fun PlayerPanel() {
    if (playerUrl.value.isNotBlank()) {
        Row(modifier = Modifier.fillMaxWidth().height(playerHeight.value.dp)) {
            VideoPlayer(playerUrl.value, modifier = Modifier.fillMaxSize())
        }
        if (isSeekable.value) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 100.dp)
            ) {
                Slider(
                    value = videoTime.value,
                    onValueChange = {
                        videoTime.value = it
                        if (it > 0.99F) {
                            mediaPlayerComponent.mediaPlayer().controls().setPosition(0.99F)
                        } else {
                            mediaPlayerComponent.mediaPlayer().controls().setPosition(it)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
        VideoPlayerButtons()
    }
}