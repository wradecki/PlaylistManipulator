package com.wradecki.view.state

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.input.TextFieldValue
import com.wradecki.model.Channel
import com.wradecki.model.Group
import com.wradecki.model.SingleList

data class ListsState(
    var lists: SnapshotStateList<SingleList>,
    var groups: SnapshotStateList<Group>,
    var channels: SnapshotStateList<Channel>,
    var currentList: MutableState<SingleList?>,
    var currentGroup: MutableState<Group?>,
    var currentChannel: MutableState<Channel?>,
    var listListState: LazyListState,
    var groupListState: LazyListState,
    var channelListState: LazyListState,
    var listSearchState: MutableState<TextFieldValue>,
    var groupSearchState: MutableState<TextFieldValue>,
    var channelSearchState: MutableState<TextFieldValue>,
    var isSearching: MutableState<Boolean>,
)
