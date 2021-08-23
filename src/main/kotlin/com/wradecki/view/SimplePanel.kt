package com.wradecki.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wradecki.model.Selectable

@Composable
fun <T : Selectable> SimplePanel(
    header: String,
    items: SnapshotStateList<T>,
    listModifier: Modifier = Modifier,
    onClick: (T) -> Unit = {},
    descriptionPanel: @Composable (T) -> Unit = { Text(text = it.name, modifier = Modifier.fillMaxHeight()) }
) {
    Column(
        modifier = listModifier
            .fillMaxHeight()
    ) {
        Text(header, fontStyle = MaterialTheme.typography.h1.fontStyle, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        LazyColumn(
            modifier = Modifier
                .border(
                    color = Color.Gray,
                    width = 1.dp
                )
                .padding(25.dp)
                .fillMaxSize()
        ) {
            items(items) { singleItem ->
                @OptIn(ExperimentalFoundationApi::class)
                (Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clickable {
                            onClick(singleItem)
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 25.dp)
                        ) {
                            val rememberedItem = remember { mutableStateOf(singleItem.selected) }
                            rememberedItem.value = singleItem.selected
                            Checkbox(
                                onCheckedChange = { checked ->
                                    rememberedItem.value = checked
                                    singleItem.selected = checked
                                },
                                checked = rememberedItem.value,
                                modifier = Modifier.fillMaxHeight().padding(end = 25.dp)
                            )
                            Row(modifier = Modifier.fillMaxHeight().fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                descriptionPanel(singleItem)
                            }
                        }
                        Button(
                            onClick = {
                                items -= singleItem
                            }, modifier = Modifier
                                .background(MaterialTheme.colors.error)
                        ) {
                            Text(text = "Delete", color = MaterialTheme.colors.onError)
                        }
                    }
                })
            }
        }
        VerticalScrollbar(rememberScrollbarAdapter(rememberScrollState(0)), modifier = Modifier.width(10.dp).fillMaxHeight().padding(10.dp))
    }
}