package com.wradecki.view

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.TrayState
import androidx.compose.ui.window.rememberTrayState
import com.wradecki.model.Channel
import com.wradecki.model.Group
import com.wradecki.model.SingleList
import kotlinx.coroutines.CoroutineScope
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent
import java.awt.Component
import java.util.*

lateinit var mediaPlayerComponent: Component
lateinit var coroutineScope: CoroutineScope

lateinit var trayState: TrayState

lateinit var workspaceHeight: MutableState<Int>

lateinit var playerUrl: MutableState<String>
lateinit var playerHeight: MutableState<Int>
lateinit var isPlaying: MutableState<Boolean>
lateinit var isFullScreen: MutableState<Boolean>

lateinit var lists: SnapshotStateList<SingleList>
lateinit var groups: SnapshotStateList<Group>
lateinit var channels: SnapshotStateList<Channel>

lateinit var currentList: MutableState<SingleList?>
lateinit var currentGroup: MutableState<Group?>
lateinit var currentChannel: MutableState<Channel?>

lateinit var listListState: LazyListState
lateinit var groupListState: LazyListState
lateinit var channelListState: LazyListState

lateinit var listSearchState: MutableState<TextFieldValue>
lateinit var groupSearchState: MutableState<TextFieldValue>
lateinit var channelSearchState: MutableState<TextFieldValue>


@Composable
fun InitState() {
    coroutineScope = rememberCoroutineScope()

    trayState = rememberTrayState()

    workspaceHeight = remember { mutableStateOf(900) }

    initPlayer()
    initLists()
}

@Composable
private fun initLists() {
    lists = remember { mutableStateListOf() }
    groups = remember { mutableStateListOf() }
    channels = remember { mutableStateListOf() }

    currentList = remember { mutableStateOf(null) }
    currentGroup = remember { mutableStateOf(null) }
    currentChannel = remember { mutableStateOf(null) }


    listListState = rememberLazyListState(0)
    groupListState = rememberLazyListState(0)
    channelListState = rememberLazyListState(0)

    listSearchState = remember { mutableStateOf(TextFieldValue()) }
    groupSearchState = remember { mutableStateOf(TextFieldValue()) }
    channelSearchState = remember { mutableStateOf(TextFieldValue()) }
}

@Composable
private fun initPlayer() {
    playerUrl = remember { mutableStateOf("") }
    playerHeight = remember { mutableStateOf(400) }
    isPlaying = remember { mutableStateOf(true) }
    isFullScreen = remember { mutableStateOf(false) }

    NativeDiscovery().discover()
    mediaPlayerComponent = remember {
        if (isMacOS()) {
            CallbackMediaPlayerComponent()
        } else {
            EmbeddedMediaPlayerComponent()
        }
    }
}


private fun isMacOS(): Boolean {
    val os = System.getProperty("os.name", "generic").lowercase(Locale.ENGLISH)
    return os.indexOf("mac") >= 0 || os.indexOf("darwin") >= 0
}