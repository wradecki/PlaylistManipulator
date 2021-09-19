package com.wradecki.view.helpers

import com.wradecki.view.globalState
import com.wradecki.view.playerState

fun recalculatePlayerHeight() {
    if (playerState.isFullScreen.value) {
        playerState.playerHeight.value = globalState.workspaceHeight.value - 100
    } else {
        playerState.playerHeight.value = 400
    }
}