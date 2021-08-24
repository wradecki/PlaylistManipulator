package com.wradecki.model

data class Channel(
    val id: String,
    override val name: String,
    val logoUrl: String,
    val url: String,
    val information: String = "-1"
) : Selectable(name) {
    override fun select(selected: Boolean) {
        this.selected = selected
    }
}
