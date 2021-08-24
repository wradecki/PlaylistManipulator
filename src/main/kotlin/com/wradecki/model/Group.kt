package com.wradecki.model

data class Group(override val name: String, val channels: List<Channel>) : Selectable(name) {
    override fun select(selected: Boolean) {
        this.selected = selected
        channels.forEach { it.select(selected) }
    }
}
