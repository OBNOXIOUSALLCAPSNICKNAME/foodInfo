package com.example.foodinfo.local.dto


open class RecipeExtendedDB(
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
    open val ingredients: List<IngredientOfRecipeDB>,
    open val nutrients: List<NutrientOfRecipeExtendedDB>,
    open val labels: List<LabelOfRecipeExtendedDB>
)