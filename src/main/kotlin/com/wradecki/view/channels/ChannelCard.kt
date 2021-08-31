package com.wradecki.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wradecki.model.Channel

@Composable
fun ChannelCard(singleChannel: Channel) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
        Column {
            Descriptions(
                "Name" to singleChannel.name,
                "Url" to singleChannel.url
            )
        }
        NetworkImage(url = singleChannel.logoUrl)
    }
}