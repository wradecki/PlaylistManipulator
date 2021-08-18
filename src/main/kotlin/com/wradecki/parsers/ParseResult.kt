package com.wradecki.parsers

import com.wradecki.model.SingleList

sealed interface ParseResult

data class ParseSuccess(val list: SingleList) : ParseResult
data class ParseFail(val error: String): ParseResult
