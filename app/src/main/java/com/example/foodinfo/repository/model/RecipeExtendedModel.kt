package com.example.foodinfo.repository.model


data class RecipeExtendedModel(
    val ID: String,
    val name: String,
    val weight: Int,
    val cookingTime: Int,
    val servings: Int,
    val preview: EdamamImageURL,
    val isFavorite: Boolean,
    val ingredientsPreviews: List<String>,
    val categories: List<CategoryOfRecipeModel>,
    val energy: NutrientOfRecipeModel,
    val protein: NutrientOfRecipeModel,
    val carb: NutrientOfRecipeModel,
    val fat: NutrientOfRecipeModel
)