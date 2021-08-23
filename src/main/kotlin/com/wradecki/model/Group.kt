package com.wradecki.model

data class Group(override val name: String, val channels: List<Channel>) : Selectable(name)
