package com.wradecki.view.video

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Notification
import com.wradecki.view.globalState
import com.wradecki.view.playerState
import uk.co.caprica.vlcj.media.Media
import uk.co.caprica.vlcj.media.MediaEventAdapter
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.State
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent
import java.awt.Component

val seekableAdapter = SeekableAdapter()
val loggingAdapter = LoggingAdapter()

@Composable
fun VideoPlayer(url: String, modifier: Modifier = Modifier) {
    VideoPlayerImpl(url, modifier = modifier)
}

@Composable
internal fun VideoPlayerImpl(url: String, modifier: Modifier) {
    SideEffect {
        val mediaPlayer = playerState.mediaPlayerComponent.mediaPlayer()
        val ok = mediaPlayer.media().play(url)
        mediaPlayer.media().events().addMediaEventListener(seekableAdapter)
        mediaPlayer.media().events().addMediaEventListener(loggingAdapter)
    }
    return SwingPanel(
        background = Color.Transparent,
        modifier = modifier,
        factory = {
            playerState.mediaPlayerComponent
        }
    )
}

fun Component.mediaPlayer(): MediaPlayer {
    return when (this) {
        is CallbackMediaPlayerComponent -> mediaPlayer()
        is EmbeddedMediaPlayerComponent -> mediaPlayer()
        else -> throw IllegalArgumentException("You can only call mediaPlayer() on vlcj player component")
    }
}

class SeekableAdapter : MediaEventAdapter() {
    override fun mediaStateChanged(media: Media?, newState: State?) {
        println("Seekable adapter: $this with state: $newState")
        if (newState == State.PLAYING && playerState.isSeekable.value != playerState.mediaPlayerComponent.mediaPlayer().status().isSeekable)
            playerState.isSeekable.value = playerState.mediaPlayerComponent.mediaPlayer().status().isSeekable
    }
}

class LoggingAdapter : MediaEventAdapter() {
    override fun mediaStateChanged(media: Media?, newState: State?) {
        if (newState == State.ERROR) {
            globalState.trayState.sendNotification(Notification(
                title = "Error",
                message = "Error on playing media",
                type = Notification.Type.Error
            ))
        }
    }

}
