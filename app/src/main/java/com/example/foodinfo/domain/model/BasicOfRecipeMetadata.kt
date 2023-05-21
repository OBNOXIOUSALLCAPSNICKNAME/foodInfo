package com.example.foodinfo.domain.model


data class BasicOfRecipeMetadata(
    val ID: Int,
    val tag: String?,
    val name: String,
    val columnName: String,
    val measure: String,
    val precision: Int,
    val rangeMin: Float,
    val rangeMax: Float,
    val stepSize: Float
)