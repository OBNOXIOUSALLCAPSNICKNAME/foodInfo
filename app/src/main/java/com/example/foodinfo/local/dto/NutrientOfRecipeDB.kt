package com.example.foodinfo.local.dto


open class NutrientOfRecipeDB(
    open val ID: Int = 0,
    open val recipeID: String,
    open val infoID: Int,
    open val value: Float
) {

    object Columns {
        const val ID = "id"
        const val RECIPE_ID = "recipe_id"
        const val INFO_ID = "info_id"
        const val TOTAL_VALUE = "total_value"
    }

    companion object {
        const val TABLE_NAME = "nutrient_of_recipe"
    }
}