package com.wradecki.view.state

import androidx.compose.runtime.MutableState
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.window.TrayState
import kotlinx.coroutines.CoroutineScope

data class GlobalState(
    var coroutineScope: CoroutineScope,
    var workspaceHeight: MutableState<Int>,
    var trayState: TrayState,
    var hiddenFocus: FocusRequester,
)