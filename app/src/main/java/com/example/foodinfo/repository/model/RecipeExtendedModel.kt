package com.example.foodinfo.repository.model

import com.example.foodinfo.utils.edamam.CookingTime
import com.example.foodinfo.utils.edamam.EdamamImageURL


data class RecipeExtendedModel(
    val ID: String,
    val name: String,
    val weight: Int,
    val cookingTime: CookingTime,
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