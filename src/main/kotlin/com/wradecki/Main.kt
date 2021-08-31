package com.wradecki

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.wradecki.view.*
import com.wradecki.view.helpers.recalculatePlayerHeight
import com.wradecki.view.video.PlayerPanel

fun main() = singleWindowApplication(
    title = "Playlist manipulator",
    icon = MyAppIcon,
    state = WindowState(size = WindowSize(1600.dp, 900.dp))
) {
    App()
}

@Composable
@Preview
private fun FrameWindowScope.App() {
    InitState()

    DesktopMaterialTheme(colors = darkColors()) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
            Tray(
                state = trayState,
                icon = TrayIcon
            )
            MenuRow()
            Column(
                modifier = Modifier.fillMaxSize().onSizeChanged {
                    workspaceHeight.value = it.height
                    recalculatePlayerHeight()
                }) {
                PlayerPanel()
                ListsPanel()
            }
        }
    }
}



