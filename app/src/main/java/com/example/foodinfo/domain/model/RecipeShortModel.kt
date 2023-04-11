package com.example.foodinfo.domain.model

import com.example.foodinfo.utils.edamam.CookingTime
import com.example.foodinfo.utils.edamam.EdamamImageURL


data class RecipeShortModel(
    val ID: String,
    val name: String,
    val calories: String,
    val servings: String,
    val cookingTime: CookingTime,
    val ingredientsCount: String,
    val preview: EdamamImageURL,
    val isFavorite: Boolean
)