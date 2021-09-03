package com.wradecki.exporters

import com.wradecki.model.Channel
import com.wradecki.model.Group
import com.wradecki.model.SingleList
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

internal class SltvContentMakerTest {
    private val sut = SltvContentMaker()

    private val groupName = "group name"

    private val firstId = "channel-id"
    private val firstName = "channel name"
    private val firstLogo = "logo url"
    private val firstUrl = "channel.url"

    private val secondId = "second-channel-id"
    private val secondName = "second channel name"
    private val secondLogo = "logo url for second"
    private val secondUrl = "channel.url/second"


    private val exampleList = SingleList(
        fileName = "someName",
        path = "somePath",
        listOf(
            Group(
                name = groupName,
                channels = listOf(
                    Channel(
                        id = firstId,
                        name = firstName,
                        logoUrl = firstLogo,
                        url = firstUrl,
                    ),
                    Channel(
                        id = secondId,
                        name = secondName,
                        logoUrl = secondLogo,
                        url = secondUrl
                    ),
                )
            )
        )
    )

    @Test
    internal fun `When create ----- Then should have header`() {
        val lines = sut.makeContent(emptyList())

        lines[0] shouldBe "#EXTM3U"
    }

    @Test
    internal fun `When create with no list ----- Then empty file`() {
        val contentLines = sut.makeContent(emptyList())

        contentLines.size shouldBe 1
    }


    @Test
    internal fun `When create with list but no group ----- Then empty file`() {
        val contentLines = sut.makeContent(listOf(SingleList("name", "path", emptyList())))

        contentLines.size shouldBe 1
    }

    @Test
    internal fun `When create with list and group but no channel ----- Then empty file`() {
        val contentLines = sut.makeContent(listOf(SingleList("name", "path", listOf(Group("name", emptyList())))))

        contentLines.size shouldBe 1
    }

    @Test
    internal fun `When there is channel ----- Then first line is description line`() {
        exampleList.select(true)
        val contentLines = sut.makeContent(listOf(exampleList))

        println(contentLines)

        contentLines[1] shouldBeEqualTo """#EXTINF:-1 tvg-id="$firstId" tvg-name="$firstName" tvg-logo="$firstLogo" group-title="$groupName",$firstName"""
    }

    @Test
    internal fun `When there is channel ----- Then second line is url line`() {
        exampleList.select(true)
        val contentLines = sut.makeContent(listOf(exampleList))

        contentLines[2] shouldBeEqualTo firstUrl
    }

    @Test
    internal fun `When there are two channel ----- Then second channel is there`() {
        exampleList.select(true)
        val contentLines = sut.makeContent(listOf(exampleList))

        contentLines[3] shouldBeEqualTo """#EXTINF:-1 tvg-id="$secondId" tvg-name="$secondName" tvg-logo="$secondLogo" group-title="$groupName",$secondName"""
        contentLines[4] shouldBeEqualTo secondUrl
    }


}