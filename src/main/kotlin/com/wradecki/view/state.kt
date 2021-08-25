package com.wradecki.view

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.window.TrayState
import androidx.compose.ui.window.rememberTrayState
import com.wradecki.model.Channel
import com.wradecki.model.Group
import com.wradecki.model.SingleList

lateinit var trayState: TrayState
lateinit var lists: SnapshotStateList<SingleList>
lateinit var groups: SnapshotStateList<Group>
lateinit var channels: SnapshotStateList<Channel>
lateinit var currentList: MutableState<SingleList?>
lateinit var currentGroup: MutableState<Group?>

@Composable
fun InitState() {
    trayState = rememberTrayState()

    lists = remember {
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

    groups = remember { mutableStateListOf() }
    channels = remember { mutableStateListOf() }
    currentList = remember { mutableStateOf(null) }
    currentGroup = remember { mutableStateOf(null) }
}