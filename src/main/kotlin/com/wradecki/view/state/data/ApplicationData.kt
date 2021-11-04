package com.wradecki.view.state.data

import com.wradecki.model.Channel
import com.wradecki.model.Group
import com.wradecki.model.SingleList
import kotlinx.serialization.Serializable

@Serializable
data class ApplicationData(
    val listsData: ListsData,
    val playerData: PlayerData = PlayerData()
)


@Serializable
data class ListsData(
    var lists: List<SingleList>,
    var groups: List<Group>,
    var channels: List<Channel>,
    var currentList: SingleList?,
    var currentGroup: Group?,
    var currentChannel: Channel?,
    var listSearch: String,
    var groupSearch: String,
    var channelSearch: String,
)

@Serializable
data class PlayerData(
    var volume: Float = 1F
)