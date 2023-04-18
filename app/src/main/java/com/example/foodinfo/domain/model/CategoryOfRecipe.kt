package com.example.foodinfo.domain.model


data class CategoryOfRecipe(
    val name: String,
    val labels: List<LabelOfRecipe>
)