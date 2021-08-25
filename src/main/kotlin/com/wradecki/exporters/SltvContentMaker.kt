package com.wradecki.exporters

import com.wradecki.model.SingleList

class SltvContentMaker : ContentMaker {
    private val header = "#EXTM3U"

    override fun makeContent(lists: List<SingleList>): List<String> {
        val result = mutableListOf<String>()

        result += header

        for (list in lists) {
            for (group in list.groups) {
                for (channel in group.channels.filter { it.selected }) {
                    val descriptionLine =
                        """#EXTINF:${channel.information} tvg-id="${channel.id}" tvg-name="${channel.name}" tvg-logo="${channel.logoUrl}" group-title="${group.name}",${channel.name}"""
                    result += descriptionLine
                    result += channel.url
                }
            }
        }

        return result
    }

}