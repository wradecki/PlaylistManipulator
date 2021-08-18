package com.wradecki.parsers

import com.wradecki.model.Channel
import com.wradecki.model.Group
import com.wradecki.model.SingleList
import java.io.File

class SltvParser : Parser {
    private val header = "#EXTM3U"

    override fun parse(filePath: String): ParseResult {
        val file = File(filePath)
        val lines = file.readLines()
        if (lines.isEmpty())
            return ParseFail(EMPTY)
        if (lines[0] != header) {
            return ParseFail(NO_HEADER)
        }

        val channelsMap = parseChannels(lines)
        val groups = createGroups(channelsMap)

        return ParseSuccess(SingleList(file.name, file.path, groups))
    }

    private fun createGroups(groupsTempMap: MutableMap<GroupName, MutableList<Channel>>): List<Group> {
        val groups = groupsTempMap.entries.map {
            Group(it.key, it.value)
        }
        return groups
    }

    private fun parseChannels(lines: List<String>): MutableMap<GroupName, MutableList<Channel>> {
        val channels = mutableMapOf<GroupName, MutableList<Channel>>()
        for (index in 1 until lines.size step 2) {
            parseSingleChannel(lines, index, channels)
        }
        return channels
    }

    private fun parseSingleChannel(
        lines: List<String>,
        index: Int,
        channelsMap: MutableMap<GroupName, MutableList<Channel>>
    ) {
        val infoLine = lines[index]
        val groupName = findValueFor("group-title", infoLine)
        val channel = createChannel(lines, index, infoLine)
        val channels = channelsMap.getOrDefault(groupName, mutableListOf())
        channels += channel
        channelsMap[groupName] = channels
    }

    private fun createChannel(lines: List<String>, index: Int, infoLine: String): Channel {
        val url = lines[index + 1]
        val channelId = findValueFor("tvg-id", infoLine)
        val channelName = findValueFor("tvg-name", infoLine)
        val channelLogoUrl = findValueFor("tvg-logo", infoLine)
        val channel = Channel(channelId, channelName, channelLogoUrl, url)
        return channel
    }

    private fun findValueFor(tagName: String, infoLine: String) = "$tagName=\"(.*?)\"".toRegex().find(infoLine)?.groupValues?.get(1) ?: ""

}

typealias GroupName = String
