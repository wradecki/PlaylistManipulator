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

lateinit var coroutineScope: CoroutineScope

lateinit var trayState: TrayState

lateinit var lists: SnapshotStateList<SingleList>
lateinit var groups: SnapshotStateList<Group>
lateinit var channels: SnapshotStateList<Channel>

lateinit var currentList: MutableState<SingleList?>
lateinit var currentGroup: MutableState<Group?>

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

    lists = remember { mutableStateListOf() }
    groups = remember { mutableStateListOf() }
    channels = remember { mutableStateListOf() }

    currentList = remember { mutableStateOf(null) }
    currentGroup = remember { mutableStateOf(null) }


    listListState = rememberLazyListState(0)
    groupListState = rememberLazyListState(0)
    channelListState = rememberLazyListState(0)

    listSearchState = remember { mutableStateOf(TextFieldValue()) }
    groupSearchState = remember { mutableStateOf(TextFieldValue()) }
    channelSearchState = remember { mutableStateOf(TextFieldValue()) }
}