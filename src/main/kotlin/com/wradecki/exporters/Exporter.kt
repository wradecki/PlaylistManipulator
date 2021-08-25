package com.wradecki.exporters

import com.wradecki.model.SingleList

interface Exporter {
    fun export(lists: List<SingleList>, file: String): ExportResult
}