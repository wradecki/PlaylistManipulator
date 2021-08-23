package com.wradecki.model

data class SingleList(val fileName: String, val path: String, val groups: List<Group>) : Selectable(fileName)
