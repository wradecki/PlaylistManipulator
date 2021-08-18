package com.wradecki.model

data class Channel(
    val id: String,
    val name: String,
    val logoUrl: String,
    val url: String,
    val information: String = "-1"
)
