package com.wradecki.model

import kotlinx.serialization.Serializable

@Serializable
data class SingleList(val fileName: String, val path: String, val groups: List<Group>, override val name: String = fileName, override var selected: Boolean = false) : Selectable {
    override fun select(selected: Boolean) {
        this.selected = selected
        groups.forEach { it.select(selected) }
    }
}
