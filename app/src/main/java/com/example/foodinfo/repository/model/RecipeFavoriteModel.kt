package com.example.foodinfo.repository.model

import com.example.foodinfo.utils.edamam.EdamamImageURL


data class RecipeFavoriteModel(
    val ID: String,
    val name: String,
    val calories: String,
    val source: String,
    val servings: String,
    val preview: EdamamImageURL
)