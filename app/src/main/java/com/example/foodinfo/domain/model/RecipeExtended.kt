package com.example.foodinfo.domain.model

import com.example.foodinfo.core.utils.edamam.EdamamImageURL


data class RecipeExtended(
    val ID: String,
    val name: String,
    val weight: Int,
    val cookingTime: Int,
    val servings: Int,
    val preview: EdamamImageURL,
    val isFavorite: Boolean,
    val labels: List<LabelOfRecipe>,
    val nutrients: List<NutrientOfRecipe>,
    val ingredients: List<IngredientOfRecipe>
)