// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.wradecki.model.Channel
import com.wradecki.model.Group
import com.wradecki.model.SingleList
import com.wradecki.view.*


fun main() = singleWindowApplication(
    title = "Playlist manipulator",
    icon = MyAppIcon,
    state = WindowState(size = WindowSize(1920.dp, 1080.dp))
) {
    App()
}

@Composable
@Preview
private fun FrameWindowScope.App() {
    val trayState = rememberTrayState()

    val lists = remember {
        mutableStateListOf(
            SingleList(
                "some",
                "path",
                listOf(Group("jakas", listOf(Channel("someId", "some name", "http://logo.sltv.site/logo/franceT-220x132/RMCACCESS1.png", "http://ip.sltv.be:8080/3hmm2173811/t9M4av1LUm/198241"))))
            ),
            SingleList("second", "path", listOf(Group("inna", emptyList()), Group("trzecia", emptyList()))),
            SingleList("third longer name, much longer", "path", emptyList())
        )
    }

    val groups = remember { mutableStateListOf<Group>() }
    val channels = remember { mutableStateListOf<Channel>() }

    var currentList = remember { mutableStateOf<SingleList?>(null) }
    var currentGroup = remember { mutableStateOf<Group?>(null) }

    DesktopMaterialTheme(colors = darkColors()) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
            Tray(
                state = trayState,
                icon = TrayIcon
            )
            MenuRow(lists, trayState)
            Row {
                SimplePanel("Lists",
                    listModifier = Modifier.width(300.dp), items = lists,
                    onClick = {
                        groups.clear()
                        groups += it.groups
                        channels.clear()
                        currentList.value = it
                    }, onSelect = { item, checked ->
                        refreshCurrentList(currentList, item, groups, currentGroup, channels)
                    }
                )
                SimplePanel("Groups",
                    listModifier = Modifier.width(400.dp),
                    items = groups,
                    onClick = {
                        channels.clear()
                        channels += it.channels
                        currentGroup.value = it
                        refreshCurrentGroup(currentGroup, channels)
                    })
                SimplePanel("Channels", listModifier = Modifier.fillMaxWidth(), items = channels) { singleChannel ->
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
    }
}

