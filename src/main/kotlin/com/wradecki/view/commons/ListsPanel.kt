package com.wradecki.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wradecki.model.SingleList
import kotlinx.coroutines.launch

@Composable
fun ListsPanel() {
    Row {
        SimplePanel("Lists",
            listModifier = Modifier.width(300.dp),
            items = listsState.lists,
            currentItem = listsState.currentList.value,
            lazyListState = listsState.listListState,
            onClick = {
                listsState.groups.clear()
                listsState.groups += it.groups
                listsState.channels.clear()
                listsState.currentList.value = it
            }, onSelect = { item, _ ->
                refreshCurrentList(item)
            }
        )
        SimplePanel("Groups",
            listModifier = Modifier.width(400.dp),
            items = listsState.groups,
            currentItem = listsState.currentGroup.value,
            lazyListState = listsState.groupListState,
            onClick = {
                listsState.channels.clear()
                listsState.channels += it.channels
                listsState.currentGroup.value = it
                refreshCurrentGroup()
            }, onSelect = { _, _ ->
                refreshCurrentGroup()
            })
        SimplePanel(
            "Channels (${listsState.currentChannel.value?.name ?: String()})",
            listModifier = Modifier.fillMaxWidth(),
            items = listsState.channels,
            lazyListState = listsState.channelListState,
            currentItem = listsState.currentChannel.value,
            onClick = {
                playerState.playerUrl.value = it.url
                playerState.videoTime.value = 0F
                playerState.isPlaying.value = true
                listsState.currentChannel.value = it
            }
        ) { singleChannel ->
            ChannelCard(singleChannel)
        }
    }
}


private fun refreshCurrentList(
    item: SingleList
) {
    if (listsState.currentList.value == item) {
        listsState.groups.clear()
        listsState.groups += listsState.currentList.value!!.groups
        globalState.coroutineScope.launch {
            listsState.groupListState.animateScrollToItem(index = 0)
        }
        refreshCurrentGroup()
    }
}

private fun refreshCurrentGroup() {
    if (listsState.currentGroup.value != null) {
        listsState.channels.clear()
        listsState.channels += listsState.currentGroup.value!!.channels
        globalState.coroutineScope.launch {
            listsState.channelListState.scrollToItem(index = 0)
        }
    }
}
