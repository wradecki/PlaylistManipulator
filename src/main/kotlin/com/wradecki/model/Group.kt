package com.wradecki.model

import kotlinx.serialization.Serializable

@Serializable
data class Group(override val name: String, val channels: List<Channel>, override var selected: Boolean = false) : Selectable {
    override fun select(selected: Boolean) {
        this.selected = selected
        channels.forEach { it.select(selected) }
    }
}
