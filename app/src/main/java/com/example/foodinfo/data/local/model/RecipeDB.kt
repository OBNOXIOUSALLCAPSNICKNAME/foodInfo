package com.example.foodinfo.data.local.model


open class RecipeDB(
    open val ID: String,
    open val source: String,
    open val name: String,
    open val previewURL: String,
    open val calories: Int,
    open val ingredientsCount: Int,
    open val weight: Int,
    open val cookingTime: Int,
    open val servings: Int,
    open val isFavorite: Boolean = false,
    open val lastUpdate: Long = System.currentTimeMillis(),
) {

    object Columns {
        const val ID = "id"
        const val SOURCE = "source"
        const val NAME = "name"
        const val PREVIEW_URL = "preview_url"
        const val CALORIES = "calories"
        const val INGREDIENTS_COUNT = "ingredients"
        const val WEIGHT = "weight"
        const val COOKING_TIME = "time"
        const val SERVINGS = "servings"
        const val IS_FAVORITE = "is_favorite"
        const val LAST_UPDATE = "last_update"
    }

    companion object {
        const val TABLE_NAME = "recipe"
    }
}