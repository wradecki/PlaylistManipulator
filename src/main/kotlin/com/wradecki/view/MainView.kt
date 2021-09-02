package com.wradecki.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import com.wradecki.view.helpers.recalculatePlayerHeight
import com.wradecki.view.video.PlayerPanel

@Composable
fun MainView() {
    Column(
        modifier = Modifier.fillMaxSize().onSizeChanged {
            workspaceHeight.value = it.height
            recalculatePlayerHeight()
        }) {
        PlayerPanel()
        ListsPanel()
    }
}