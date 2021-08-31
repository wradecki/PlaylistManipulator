package com.wradecki.view.video

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import com.wradecki.view.isSeekable
import com.wradecki.view.mediaPlayerComponent
import uk.co.caprica.vlcj.media.Media
import uk.co.caprica.vlcj.media.MediaEventAdapter
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.State
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent

val seekableAdapter = SeekableAdapter()

@Composable
fun VideoPlayer(url: String, modifier: Modifier = Modifier) {
    VideoPlayerImpl(url, modifier = modifier)
}

@Composable
internal fun VideoPlayerImpl(url: String, modifier: Modifier) {
    SideEffect {
        val mediaPlayer = mediaPlayerComponent.mediaPlayer()
        val ok = mediaPlayer.media().play(url)
        mediaPlayer.media().events().addMediaEventListener(seekableAdapter)
    }
    return SwingPanel(
        background = Color.Transparent,
        modifier = modifier,
        factory = {
            mediaPlayerComponent
        }
    )
}

fun Any.mediaPlayer(): MediaPlayer {
    return when (this) {
        is CallbackMediaPlayerComponent -> mediaPlayer()
        is EmbeddedMediaPlayerComponent -> mediaPlayer()
        else -> throw IllegalArgumentException("You can only call mediaPlayer() on vlcj player component")
    }
}

class SeekableAdapter : MediaEventAdapter() {
    override fun mediaStateChanged(media: Media?, newState: State?) {
        println("media: $media, newState: $newState")
        if (newState == State.PLAYING && isSeekable.value != mediaPlayerComponent.mediaPlayer().status().isSeekable)
            isSeekable.value = mediaPlayerComponent.mediaPlayer().status().isSeekable
    }
}