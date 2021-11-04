package com.wradecki.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.wradecki.view.helpers.recalculatePlayerHeight
import com.wradecki.view.video.PlayerPanel

@Composable
fun MainView() {
    Column(
        modifier = Modifier.fillMaxSize().focusRequester(globalState.hiddenFocus).clickable { globalState.hiddenFocus.requestFocus() }
            .onSizeChanged() {
                globalState.workspaceHeight.value = it.height
                recalculatePlayerHeight()
            }) {
        PlayerPanel()
        ListsPanel()
        TextField(modifier = Modifier.width(0.dp).height(0.dp), value = TextFieldValue(), onValueChange = {})
    }
}