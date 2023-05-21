package com.example.foodinfo.data.local.model


open class NutrientOfRecipeMetadataDB(
    open val ID: Int,
    open val tag: String,
    open val name: String,
    open val measure: String,
    open val description: String,
    open val hasRDI: Boolean,
    open val previewURL: String,
    open val dailyAllowance: Float,
    open val precision: Int,
    open val stepSize: Float,
    open val rangeMin: Float,
    open val rangeMax: Float
) {

    object Columns {
        const val ID = "id"
        const val TAG = "tag"
        const val NAME = "name"
        const val MEASURE = "measure"
        const val DESCRIPTION = "description"
        const val PREVIEW_URL = "preview_url"
        const val HAS_RDI = "has_rdi"
        const val DAILY_ALLOWANCE = "daily_allowance"
        const val PRECISION = "precision"
        const val STEP_SIZE = "step_size"
        const val RANGE_MIN = "range_min"
        const val RANGE_MAX = "range_max"
    }

    companion object {
        const val TABLE_NAME = "nutrient_recipe_metadata"
    }
}