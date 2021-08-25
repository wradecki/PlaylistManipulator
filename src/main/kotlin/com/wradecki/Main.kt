package com.wradecki

import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.wradecki.model.Channel
import com.wradecki.model.Group
import com.wradecki.model.SingleList
import com.wradecki.view.*
import kotlinx.coroutines.launch

fun main() = singleWindowApplication(
    title = "Playlist manipulator",
    icon = MyAppIcon,
    state = WindowState(size = WindowSize(1600.dp, 900.dp))
) {
    App()
}

@Composable
@Preview
private fun FrameWindowScope.App() {
    InitState()

    DesktopMaterialTheme(colors = darkColors()) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
            Tray(
                state = trayState,
                icon = TrayIcon
            )
            MenuRow(lists, trayState)
            Row {
                SimplePanel("Lists",
                    listModifier = Modifier.width(300.dp),
                    items = lists,
                    currentItem = currentList.value,
                    lazyListState = listListState,
                    onClick = {
                        groups.clear()
                        groups += it.groups
                        channels.clear()
                        currentList.value = it
                    }, onSelect = { item, _ ->
                        refreshCurrentList(currentList, item, groups, currentGroup, channels)
                    }
                )
                SimplePanel("Groups",
                    listModifier = Modifier.width(400.dp),
                    items = groups,
                    currentItem = currentGroup.value,
                    lazyListState = groupListState,
                    onClick = {
                        channels.clear()
                        channels += it.channels
                        currentGroup.value = it
                        refreshCurrentGroup(currentGroup, channels)
                    })
                SimplePanel(
                    "Channels",
                    listModifier = Modifier.fillMaxWidth(),
                    items = channels,
                    isClickable = false,
                    lazyListState = channelListState,
                ) { singleChannel ->
                    ChannelCard(singleChannel)
                }
            }
        }
    }
}

private fun refreshCurrentList(
    currentList: MutableState<SingleList?>,
    item: SingleList,
    groups: SnapshotStateList<Group>,
    currentGroup: MutableState<Group?>,
    channels: SnapshotStateList<Channel>
) {
    if (currentList.value == item) {
        groups.clear()
        groups += currentList.value!!.groups
        coroutineScope.launch {
            groupListState.animateScrollToItem(index = 0)
        }
        refreshCurrentGroup(currentGroup, channels)
    }
}

private fun refreshCurrentGroup(
    currentGroup: MutableState<Group?>,
    channels: SnapshotStateList<Channel>
) {
    if (currentGroup.value != null) {
        channels.clear()
        channels += currentGroup.value!!.channels
        coroutineScope.launch {
            channelListState.scrollToItem(index = 0)
        }
    }
}

