package com.wradecki.view.video

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wradecki.view.isSeekable
import com.wradecki.view.mediaPlayerComponent
import com.wradecki.view.videoTime

@Composable
fun PlaytimeSlider() {
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
}