package com.example.foodinfo.domain.model

import com.example.foodinfo.utils.edamam.EdamamImageURL


data class Recipe(
    val ID: String,
    val source: String,
    val name: String,
    val preview: EdamamImageURL,
    val calories: Int,
    val ingredientsCount: Int,
    val weight: Int,
    val cookingTime: Int,
    val servings: Int,
    val isFavorite: Boolean
)