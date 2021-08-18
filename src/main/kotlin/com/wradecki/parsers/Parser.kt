package com.wradecki.parsers

interface Parser {
    fun parse(file: String): ParseResult
}