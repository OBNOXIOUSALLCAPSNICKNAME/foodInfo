package com.example.foodinfo.data.local.model


open class SearchFilterDB(
    open val name: String = DEFAULT_NAME
) {

    object Columns {
        const val NAME = "name"
    }

    companion object {
        const val DEFAULT_NAME = "default filter"
        const val TABLE_NAME = "search_filter"
    }
}