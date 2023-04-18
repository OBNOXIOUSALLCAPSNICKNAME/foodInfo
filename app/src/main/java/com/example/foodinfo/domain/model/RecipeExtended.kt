package com.example.foodinfo.domain.model


data class RecipeExtended(
    val ID: String,
    val name: String,
    val weight: Int,
    val cookingTime: Int,
    val servings: Int,
    val preview: String,
    val isFavorite: Boolean,
    val ingredientsPreviews: List<String>,
    val categories: List<CategoryOfRecipe>,
    val energy: NutrientOfRecipe,
    val protein: NutrientOfRecipe,
    val carb: NutrientOfRecipe,
    val fat: NutrientOfRecipe
)