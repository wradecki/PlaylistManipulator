package com.wradecki

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.wradecki.view.*
import com.wradecki.view.video.*


@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    InitState()
    Window(
        ::exit,
        state = WindowState(size = WindowSize(1600.dp, 900.dp)),
        title = "Playlist Manipulator",
        icon = MyAppIcon,
        onKeyEvent = {
            val playerState: Boolean = playerState.playerUrl.value.isNotEmpty()
            if (playerIsActive(playerState, it)) {
                handlePlayerKeyboard(it)
            } else if (it.key == Key.Escape){
                globalState.hiddenFocus.requestFocus()
            }
            println(it)
            println(it.nativeKeyEvent.component)
            true
        }

    ) {
        App()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
private fun handlePlayerKeyboard(it: KeyEvent) {
    when (it.key) {
        Key.Spacebar -> pausePlayPlayer()
        Key.N -> nextPlayer()
        Key.B -> prevPlayer()
        Key.F -> fullScreenPlayer()
        Key.S -> stopPlayer()
    }
}

private fun playerIsActive(playerState: Boolean, it: KeyEvent) = playerState && it.type == KeyEventType.KeyUp && !listsState.isSearching.value

fun ApplicationScope.exit() {
    stopPlayer()
    saveState()
    exitApplication()
}

@Composable
@Preview
private fun FrameWindowScope.App() {

    DesktopMaterialTheme(colors = darkColors()) {
        Surface(modifier = Modifier.fillMaxSize().focusTarget(), color = MaterialTheme.colors.surface) {
            Tray(
                state = globalState.trayState,
                icon = MyAppIcon
            )
            MenuRow()
            MainView()
        }
    }
}



