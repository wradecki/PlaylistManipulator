package com.wradecki.view.helpers

import com.wradecki.view.isFullScreen
import com.wradecki.view.playerHeight
import com.wradecki.view.workspaceHeight

fun recalculatePlayerHeight() {
    if (isFullScreen.value) {
        playerHeight.value = workspaceHeight.value - 100
    } else {
        playerHeight.value = 400
    }
}