package com.wradecki

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.wradecki.view.*
import com.wradecki.view.video.stopPlayer


fun main() = application {
    InitState()
    Window(
        ::exit,
        state = WindowState(size = WindowSize(1600.dp, 900.dp)),
        title = "Playlist Manipulator",
        icon = MyAppIcon,
    ) {
        App()
    }
}

fun ApplicationScope.exit() {
    stopPlayer()
    saveState()
    exitApplication()
}

@Composable
@Preview
private fun FrameWindowScope.App() {

    DesktopMaterialTheme(colors = darkColors()) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
            Tray(
                state = globalState.trayState,
                icon = MyAppIcon
            )
            MenuRow()
            MainView()
        }
    }
}



