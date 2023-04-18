package com.example.foodinfo.domain.model


data class Recipe(
    val ID: String,
    val name: String,
    val weight: Int,
    val cookingTime: Int,
    val servings: Int,
    val previewURL: String,
    val isFavorite: Boolean
)