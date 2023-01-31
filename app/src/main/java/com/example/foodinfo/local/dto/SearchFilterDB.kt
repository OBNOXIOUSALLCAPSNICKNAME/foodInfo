package com.example.foodinfo.local.dto


open class SearchFilterDB(
    open val name: String = DEFAULT_NAME
) {

    object Columns {
        const val NAME = "name"
    }

    companion object {
        const val DEFAULT_NAME = "default_name"
        const val TABLE_NAME = "search_filter"
    }
}