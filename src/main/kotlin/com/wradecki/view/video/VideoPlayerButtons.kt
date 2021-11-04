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
import com.wradecki.view.helpers.recalculatePlayerHeight
import com.wradecki.view.listsState
import com.wradecki.view.playerState
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
            fullScreenPlayer()
        }) {
        Icon(imageVector = if (playerState.isFullScreen.value) FontAwesomeIcons.Solid.CompressArrowsAlt else FontAwesomeIcons.Solid.ExpandArrowsAlt, "")
    }
}

fun fullScreenPlayer() {
    playerState.isFullScreen.value = playerState.isFullScreen.value.not()
    recalculatePlayerHeight()
}


@Composable
private fun NextChannelButton() {
    Button(
        onClick = {
            nextPlayer()
        }, enabled = (listsState.currentChannel.value != null && listsState.channels.lastIndexOf(listsState.currentChannel.value) < listsState.channels.lastIndex)
    ) {
        Icon(imageVector = FontAwesomeIcons.Solid.StepForward, "")
    }
}

fun nextPlayer() {
    if (isSelected()) {
        var index = listsState.channels.lastIndexOf(listsState.currentChannel.value)
        if (index < listsState.channels.lastIndex) {
            index++
        }
        listsState.currentChannel.value = listsState.channels[index]
        playerState.mediaPlayerComponent.mediaPlayer().media().play(listsState.currentChannel.value!!.url)
        playerState.isPlaying.value = true
    }
}

@Composable
private fun StopButton() {
    Button(onClick = {
        stopPlayer()
    }) {
        Icon(imageVector = FontAwesomeIcons.Solid.Stop, "")
    }
}

fun stopPlayer() {
    playerState.mediaPlayerComponent.mediaPlayer().controls().stop()
    playerState.playerUrl.value = ""
    playerState.isPlaying.value = false
}

@Composable
private fun PauseButton() {
    if (playerState.isPlaying.value) {
        Button(onClick = {
            pausePlayer()
        }) {
            Icon(imageVector = FontAwesomeIcons.Solid.Pause, "")
        }
    }
}

private fun pausePlayer() {
    playerState.mediaPlayerComponent.mediaPlayer().controls().pause()
    playerState.isPlaying.value = false
}

@Composable
private fun PlayButton() {
    if (!playerState.isPlaying.value) {
        Button(onClick = {
            playPlayer()
        }) {
            Icon(imageVector = FontAwesomeIcons.Solid.Play, "")
        }
    }
}

private fun playPlayer() {
    playerState.mediaPlayerComponent.mediaPlayer().controls().play()
    playerState.isPlaying.value = true
}

fun pausePlayPlayer() {
    if (playerState.isPlaying.value) {
        pausePlayer()
    } else {
        playPlayer()
    }
}

@Composable
private fun PreviousChannelButton() {
    Button(
        onClick = {
            prevPlayer()
        }, enabled = listsState.currentChannel.value != null && listsState.channels.lastIndexOf(listsState.currentChannel.value) > 0
    ) {
        Icon(imageVector = FontAwesomeIcons.Solid.StepBackward, "")
    }
}

fun prevPlayer() {
    if (isSelected()) {
        var index = listsState.channels.lastIndexOf(listsState.currentChannel.value)
        if (index > 0) {
            index--
        }
        listsState.currentChannel.value = listsState.channels[index]
        playerState.mediaPlayerComponent.mediaPlayer().media().play(listsState.currentChannel.value!!.url)
        playerState.isPlaying.value = true
    }
}

private fun isSelected() = listsState.channels.isNotEmpty() && listsState.currentChannel.value != null
