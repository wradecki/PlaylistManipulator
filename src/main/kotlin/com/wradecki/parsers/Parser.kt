package com.wradecki.parsers

interface Parser {
    fun parse(filePath: String): ParseResult
}