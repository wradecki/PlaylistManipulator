package com.wradecki.model

abstract class Selectable(open val name: String, var selected: Boolean = false) {
    abstract fun select(selected: Boolean)
}