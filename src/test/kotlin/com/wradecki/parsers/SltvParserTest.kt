package com.wradecki.parsers

import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

internal class SltvParserTest {
    val sut: Parser = SltvParser();

    private fun String.toTestPath() = "src/test/resources/$this"
    private fun parse(path: String) = sut.parse(path.toTestPath())
    private fun String.toUnixPath(): String = this.replace("\\", "/")


    @Test
    internal fun `When empty file ------ Then return error`() {
        val result = parse("empty_file.m3u")

        result `should be instance of` ParseFail::class
        if (result is ParseFail) result.error.shouldBe(EMPTY)
    }


    @Test
    internal fun `When no header ----- Then error`() {
        val result = parse("no_header_file.m3u")

        result `should be instance of` ParseFail::class
        if (result is ParseFail) result.error.shouldBe(NO_HEADER)
    }

    @Test
    internal fun `When one group one channel ----- Then list values should be set`() {
        val fileName = "single_channel.m3u"
        val result = parse(fileName)

        result `should be instance of` ParseSuccess::class
        if (result is ParseSuccess) {
            val list = result.list
            list.fileName shouldBeEqualTo fileName
            list.path.toUnixPath() shouldBeEqualTo fileName.toTestPath().toUnixPath()
        }
    }


    @Test
    internal fun `When one group one channel ----- Then group values should be set`() {
        val fileName = "single_channel.m3u"
        val result = parse(fileName)

        result `should be instance of` ParseSuccess::class
        if (result is ParseSuccess) {
            val group = result.list.groups[0]

            group.name shouldBeEqualTo "FR FRANCE FHD"
        }
    }

    @Test
    internal fun `When one group one channel ----- Then channel values should be set`() {
        val fileName = "single_channel.m3u"
        val result = parse(fileName)

        result `should be instance of` ParseSuccess::class
        if (result is ParseSuccess) {
            val channel = result.list.groups[0].channels[0]

            channel.name shouldBeEqualTo "FR: RMC SPORT UHD"
            channel.id shouldBeEqualTo "rmcsportuhd.fr"
            channel.logoUrl shouldBeEqualTo "http://logo.sltv.site/logo/franceT-220x132/RMCACCESS1.png"
        }
    }

    @Test
    internal fun `When two groups one channel each ----- Then all values should be set`() {
        val fileName = "two_channels.m3u"
        val result = parse(fileName)

        result `should be instance of` ParseSuccess::class
        if (result is ParseSuccess) {
            val list = result.list
            list.fileName shouldBeEqualTo fileName
            list.path.toUnixPath() shouldBeEqualTo fileName.toTestPath().toUnixPath()

            list.groups[0].name shouldBeEqualTo "FR FRANCE FHD"

            result.list.groups[0].channels[0].id shouldBeEqualTo "rmcsportuhd.fr"
            result.list.groups[0].channels[0].name shouldBeEqualTo "FR: RMC SPORT UHD"
            result.list.groups[0].channels[0].logoUrl shouldBeEqualTo "http://logo.sltv.site/logo/franceT-220x132/RMCACCESS1.png"


            list.groups[1].name shouldBeEqualTo "AF AFRICA"

            result.list.groups[1].channels[0].id shouldBeEqualTo ""
            result.list.groups[1].channels[0].name shouldBeEqualTo "-----AFRICA-----"
            result.list.groups[1].channels[0].logoUrl shouldBeEqualTo "http://logo.sltv.site/logo/flag/africa.png"
        }
    }

    @Test
    internal fun `When two groups first with one channel second with two ----- Then all values should be set`() {
        val fileName = "three_channels.m3u"
        val result = parse(fileName)

        result `should be instance of` ParseSuccess::class
        if (result is ParseSuccess) {
            val list = result.list
            list.fileName shouldBeEqualTo fileName
            list.path.toUnixPath() shouldBeEqualTo fileName.toTestPath().toUnixPath()

            list.groups[0].name shouldBeEqualTo "FR FRANCE FHD"

            result.list.groups[0].channels[0].id shouldBeEqualTo "rmcsportuhd.fr"
            result.list.groups[0].channels[0].name shouldBeEqualTo "FR: RMC SPORT UHD"
            result.list.groups[0].channels[0].logoUrl shouldBeEqualTo "http://logo.sltv.site/logo/franceT-220x132/RMCACCESS1.png"


            list.groups[1].name shouldBeEqualTo "AF AFRICA"

            result.list.groups[1].channels[1].id shouldBeEqualTo ""
            result.list.groups[1].channels[1].name shouldBeEqualTo "AF: TFM SENEGAL"
            result.list.groups[1].channels[1].logoUrl shouldBeEqualTo ""
        }
    }

}