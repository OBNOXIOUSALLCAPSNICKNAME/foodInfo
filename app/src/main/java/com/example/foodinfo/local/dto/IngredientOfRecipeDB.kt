package com.example.foodinfo.local.dto


open class IngredientOfRecipeDB(
    open val ID: Int = 0,
    open val recipeID: String,
    open val text: String,
    open val quantity: Float,
    open val measure: String,
    open val weight: Float,
    open val food: String,
    open val foodCategory: String,
    open val foodID: String,
    open val previewURL: String
) {

    object Columns {
        const val ID = "id"
        const val RECIPE_ID = "recipe_id"
        const val TEXT = "text"
        const val QUANTITY = "quantity"
        const val MEASURE = "measure"
        const val WEIGHT = "weight"
        const val FOOD = "food"
        const val FOOD_CATEGORY = "food_category"
        const val FOOD_ID = "food_id"
        const val PREVIEW_URL = "preview_url"
    }

    companion object {
        const val TABLE_NAME = "ingredient_of_recipe"
        const val DEFAULT_MEASURE = "g"
        const val DEFAULT_PREVIEW = ""
        const val DEFAULT_CATEGORY = "food"
    }
}