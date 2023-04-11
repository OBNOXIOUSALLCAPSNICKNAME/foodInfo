package com.example.foodinfo.local.model


open class BasicRecipeAttrDB(
    open val ID: Int,
    open val tag: String?,
    open val name: String,
    open val columnName: String,
    open val measure: String,
    open val rangeMin: Float,
    open val rangeMax: Float,
    open val stepSize: Float
) {

    object Columns {
        const val ID = "id"
        const val TAG = "tag"
        const val NAME = "name"
        const val COLUMN_NAME = "column_name"
        const val MEASURE = "measure"
        const val RANGE_MIN = "range_min"
        const val RANGE_MAX = "range_max"
        const val STEP_SIZE = "step_size"
    }

    companion object {
        const val TABLE_NAME = "basic_recipe_attr"
    }
}