package com.wradecki.view

import androidx.compose.runtime.Composable

@Composable
fun Descriptions(vararg descriptions: Pair<String, String>) {
    descriptions.forEach {
        DescriptionLine(it)
    }
}