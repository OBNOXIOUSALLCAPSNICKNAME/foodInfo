package com.example.foodinfo.domain.model


data class LabelOfRecipeMetadata(
    val ID: Int,
    val categoryID: Int,
    val tag: String,
    val name: String,
    val description: String,
    val previewURL: String
)