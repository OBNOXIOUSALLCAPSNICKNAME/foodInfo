package com.example.foodinfo.domain.model


data class CategoryOfRecipeModel(
    val name: String,
    val labels: List<LabelShortModel>
)