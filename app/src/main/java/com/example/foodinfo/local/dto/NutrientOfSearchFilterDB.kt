package com.example.foodinfo.local.dto


open class NutrientOfSearchFilterDB(
    open val ID: Int = 0,
    open val filterName: String,
    open val infoID: Int,
    open val minValue: Float,
    open val maxValue: Float
) {

    object Columns {
        const val ID = "id"
        const val FILTER_NAME = "filter_name"
        const val INFO_ID = "info_id"
        const val MIN_VALUE = "min_value"
        const val MAX_VALUE = "max_value"
    }

    companion object {
        const val TABLE_NAME = "nutrient_of_search_filter"
    }
}