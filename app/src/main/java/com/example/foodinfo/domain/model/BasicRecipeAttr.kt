package com.example.foodinfo.domain.model


data class BasicRecipeAttr(
    val ID: Int,
    val tag: String?,
    val name: String,
    val columnName: String,
    val measure: String,
    val rangeMin: Float,
    val rangeMax: Float,
    val stepSize: Float
)