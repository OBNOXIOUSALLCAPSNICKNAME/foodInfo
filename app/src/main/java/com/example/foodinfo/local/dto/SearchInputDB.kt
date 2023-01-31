package com.example.foodinfo.local.dto


open class SearchInputDB(
    open val ID: Int,
    open val inputText: String,
    open val date: Long
) {

    object Columns {
        const val ID = "id"
        const val INPUT_TEXT = "input_text"
        const val DATE = "date"
    }

    companion object {
        const val TABLE_NAME = "search_input"
        const val LIMIT = 7
    }
}