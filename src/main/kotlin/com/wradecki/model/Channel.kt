package com.wradecki.model

import kotlinx.serialization.Serializable

@Serializable
data class Channel(
    val id: String,
    override val name: String,
    val logoUrl: String,
    val url: String,
    val information: String = "-1",
    override var selected: Boolean = false
) : Selectable {
    override fun select(selected: Boolean) {
        this.selected = selected
    }
}
