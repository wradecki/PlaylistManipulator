package com.wradecki.view.video

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ControlsPanel() {
    Row(modifier = Modifier.fillMaxWidth().height(25.dp),
        horizontalArrangement = Arrangement.SpaceBetween) {
        val sectionWidth = 400.dp
        Column(modifier = Modifier.width(sectionWidth)) {
            VolumeSlider()
        }
        Column(modifier = Modifier.width(sectionWidth).height(25.dp)) {
            VideoPlayerButtons()
        }
        Column(modifier = Modifier.width(sectionWidth)) {  }
    }
}