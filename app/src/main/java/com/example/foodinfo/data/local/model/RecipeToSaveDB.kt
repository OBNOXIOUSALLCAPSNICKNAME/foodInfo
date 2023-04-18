package com.example.foodinfo.data.local.model


open class RecipeToSaveDB(
    open val ID: String,
    open val source: String,
    open val name: String,
    open val previewURL: String,
    open val calories: Int,
    open val ingredientsCount: Int,
    open val weight: Int,
    open val cookingTime: Int,
    open val servings: Int,
    open val ingredients: List<IngredientOfRecipeDB>,
    open val nutrients: List<NutrientOfRecipeDB>,
    open val labels: List<LabelOfRecipeDB>
)