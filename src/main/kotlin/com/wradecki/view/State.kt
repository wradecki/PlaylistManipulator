package com.wradecki.view

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.rememberTrayState
import com.wradecki.view.state.GlobalState
import com.wradecki.view.state.ListsState
import com.wradecki.view.state.PlayerState
import com.wradecki.view.state.data.ApplicationData
import com.wradecki.view.state.data.ListsData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent
import java.io.File
import java.util.*

lateinit var globalState: GlobalState
lateinit var listsState: ListsState
lateinit var playerState: PlayerState

val settings = File(".settings")

@Composable
fun InitState() {
    InitGlobal()
    initPlayer()
    initLists()
    LoadState()
}


fun saveState() {
    val data = ApplicationData(
        listDataFromState(listsState)
    )

    val json = Json.encodeToString(data)

    println(settings)
    println(settings.absolutePath)
    settings.writeText(json)
}


fun listDataFromState(listsState: ListsState): ListsData {
    return ListsData(
        lists = listsState.lists,
        groups = listsState.groups,
        channels = listsState.channels,
        currentList = listsState.currentList.value,
        currentGroup = listsState.currentGroup.value,
        currentChannel = listsState.currentChannel.value,
        listSearch = listsState.listSearchState.value.text,
        groupSearch = listsState.groupSearchState.value.text,
        channelSearch = listsState.channelSearchState.value.text,
    )
}


@Composable
fun LoadState() {
    if (settings.exists()) {
        val settings = Json.decodeFromString<ApplicationData>(settings.readText())
        fromDataToState(settings)
    }
}

fun fromDataToState(data: ApplicationData) {

    listsState.lists.addAll(data.listsData.lists)
    listsState.groups.addAll(data.listsData.groups)
    listsState.channels.addAll(data.listsData.channels)
    listsState.currentList.value = data.listsData.currentList
    listsState.currentGroup.value = data.listsData.currentGroup
    listsState.currentChannel.value = data.listsData.currentChannel

    println(data.listsData.listSearch)
    println(data.listsData.groupSearch)
    println(data.listsData.channelSearch)
    listsState.listSearchState.value = TextFieldValue(data.listsData.listSearch)
    listsState.groupSearchState.value = TextFieldValue(data.listsData.groupSearch)
    listsState.channelSearchState.value = TextFieldValue(data.listsData.channelSearch)
}

@Composable
private fun InitGlobal() {
    globalState = GlobalState(
        coroutineScope = rememberCoroutineScope(),
        trayState = rememberTrayState(),
        workspaceHeight = remember { mutableStateOf(900) }
    )
}

@Composable
private fun initLists() {
    listsState = ListsState(
        lists = remember { mutableStateListOf() },
        groups = remember { mutableStateListOf() },
        channels = remember { mutableStateListOf() },
        currentList = remember { mutableStateOf(null) },
        currentGroup = remember { mutableStateOf(null) },
        currentChannel = remember { mutableStateOf(null) },
        listListState = rememberLazyListState(0),
        groupListState = rememberLazyListState(0),
        channelListState = rememberLazyListState(0),
        listSearchState = remember { mutableStateOf(TextFieldValue()) },
        groupSearchState = remember { mutableStateOf(TextFieldValue()) },
        channelSearchState = remember { mutableStateOf(TextFieldValue()) },
    )
}

@Composable
private fun initPlayer() {
    NativeDiscovery().discover()
    playerState = PlayerState(
        playerUrl = remember { mutableStateOf("") },
        playerHeight = remember { mutableStateOf(400) },
        isPlaying = remember { mutableStateOf(true) },
        isFullScreen = remember { mutableStateOf(false) },
        videoTime = remember { mutableStateOf(0F) },
        isSeekable = remember { mutableStateOf(false) },
        mediaPlayerComponent = remember {
            if (isMacOS()) {
                CallbackMediaPlayerComponent()
            } else {
                EmbeddedMediaPlayerComponent()
            }
        }
    )
}


private fun isMacOS(): Boolean {
    val os = System.getProperty("os.name", "generic").lowercase(Locale.ENGLISH)
    return os.indexOf("mac") >= 0 || os.indexOf("darwin") >= 0
}