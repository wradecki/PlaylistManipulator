package com.wradecki.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DescriptionLine(data: Pair<String, String>) {
    Row {
        Text(text = "${data.first}:", modifier = Modifier.fillMaxWidth(0.1F))
        Text(text = data.second, modifier = Modifier.fillMaxHeight())
    }
}