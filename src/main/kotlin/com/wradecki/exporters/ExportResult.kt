package com.wradecki.exporters

import com.wradecki.model.SingleList

sealed interface ExportResult

data class ExportSuccess(val lists: List<SingleList>, val file: String) : ExportResult
data class ExportFail(val error: String) : ExportResult
