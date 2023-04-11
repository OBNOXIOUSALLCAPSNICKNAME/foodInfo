package com.example.foodinfo.local.model


open class LabelOfRecipeDB(
    open val ID: Int = 0,
    open val infoID: Int,
    open val recipeID: String
) {

    object Columns {
        const val ID = "id"
        const val RECIPE_ID = "recipe_id"
        const val INFO_ID = "info_id"
    }

    companion object {
        const val TABLE_NAME = "label_of_recipe"
    }
}