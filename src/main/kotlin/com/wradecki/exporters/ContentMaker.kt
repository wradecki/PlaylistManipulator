package com.wradecki.exporters

import com.wradecki.model.SingleList

interface ContentMaker {
    fun makeContent(lists: List<SingleList>): List<String>
}