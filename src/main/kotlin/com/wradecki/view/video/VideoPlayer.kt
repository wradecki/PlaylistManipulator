package com.wradecki.view.video

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import com.wradecki.view.mediaPlayerComponent
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent

@Composable
fun VideoPlayer(url: String, modifier: Modifier = Modifier) {
    VideoPlayerImpl(url, modifier = modifier)
}

@Composable
internal fun VideoPlayerImpl(url: String, modifier: Modifier) {


    SideEffect {
        val mediaPlayer = mediaPlayerComponent.mediaPlayer()
        val ok = mediaPlayer.media().play(url)
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
