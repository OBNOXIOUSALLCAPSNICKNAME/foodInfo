package com.example.foodinfo.features.recipe.model

import com.example.foodinfo.core.utils.edamam.EdamamImageURL


data class RecipeModel(
    val ID: String,
    val name: String,
    val weight: String,
    val cookingTime: String,
    val servings: String,
    val preview: EdamamImageURL,
    val isFavorite: Boolean,
    val categories: List<CategoryVHModel>,
    val energy: NutrientModel,
    val protein: NutrientModel,
    val carb: NutrientModel,
    val fat: NutrientModel,
    val ingredientPreviews: List<String>
)