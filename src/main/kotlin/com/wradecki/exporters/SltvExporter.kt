package com.wradecki.exporters

import com.wradecki.model.SingleList
import java.io.File

class SltvExporter : Exporter {
    override fun export(lists: List<SingleList>, file: String): ExportResult {
        val contentMaker = SltvContentMaker()
        val lines = contentMaker.makeContent(lists)
        try {
            File(file).printWriter().use { out ->
                lines.forEach {
                    out.println(it)
                }
            }
        } catch (ex: Exception) {
            return ExportFail(ex.message ?: "Undefined error")
        }

        return ExportSuccess(lists, file)
    }

}