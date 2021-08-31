package com.wradecki.view.video

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wradecki.view.*
import com.wradecki.view.helpers.recalculatePlayerHeight
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.*

@Composable
fun VideoPlayerButtons() {
    Row(
        modifier = Modifier.fillMaxWidth().height(25.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        PreviousChannelButton()
        PlayButton()
        PauseButton()
        StopButton()
        NextChannelButton()
        FullScreenButton()
    }
}

@Composable
private fun FullScreenButton() {
    Button(
        onClick = {
            isFullScreen.value = isFullScreen.value.not()
            recalculatePlayerHeight()
        }) {
        Icon(imageVector = if (isFullScreen.value) FontAwesomeIcons.Solid.CompressArrowsAlt else FontAwesomeIcons.Solid.ExpandArrowsAlt, "")
    }
}


@Composable
private fun NextChannelButton() {
    Button(
        onClick = {
            if (channels.isNotEmpty()) {
                if (currentChannel.value != null) {
                    var index = channels.lastIndexOf(currentChannel.value)
                    if (index < channels.lastIndex) {
                        index++
                    }
                    currentChannel.value = channels[index]
                    mediaPlayerComponent.mediaPlayer().media().play(currentChannel.value!!.url)
                    isPlaying.value = true
                }
            }
        }, enabled = (currentChannel.value != null && channels.lastIndexOf(currentChannel.value) < channels.lastIndex)
    ) {
        Icon(imageVector = FontAwesomeIcons.Solid.StepForward, "")
    }
}

@Composable
private fun StopButton() {
    Button(onClick = {
        mediaPlayerComponent.mediaPlayer().controls().stop()
        playerUrl.value = ""
        isPlaying.value = false
    }) {
        Icon(imageVector = FontAwesomeIcons.Solid.Stop, "")
    }
}

@Composable
private fun PauseButton() {
    if (isPlaying.value) {
        Button(onClick = {
            mediaPlayerComponent.mediaPlayer().controls().pause()
            isPlaying.value = false
        }) {
            Icon(imageVector = FontAwesomeIcons.Solid.Pause, "")
        }
    }
}

@Composable
private fun PlayButton() {
    if (!isPlaying.value) {
        Button(onClick = {
            mediaPlayerComponent.mediaPlayer().controls().play()
            isPlaying.value = true
        }) {
            Icon(imageVector = FontAwesomeIcons.Solid.Play, "")
        }
    }
}

@Composable
private fun PreviousChannelButton() {
    Button(
        onClick = {
            if (channels.isNotEmpty()) {
                if (currentChannel.value != null) {
                    var index = channels.lastIndexOf(currentChannel.value)
                    if (index > 0) {
                        index--
                    }
                    currentChannel.value = channels[index]
                    mediaPlayerComponent.mediaPlayer().media().play(currentChannel.value!!.url)
                    isPlaying.value = true
                }
            }
        }, enabled = currentChannel.value != null && channels.lastIndexOf(currentChannel.value) > 0
    ) {
        Icon(imageVector = FontAwesomeIcons.Solid.StepBackward, "")
    }
}
