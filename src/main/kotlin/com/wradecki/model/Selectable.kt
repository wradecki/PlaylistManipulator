package com.wradecki.model

interface Selectable {
    val name: String
    var selected: Boolean
    fun select(selected: Boolean)
}