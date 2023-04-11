package com.example.foodinfo.domain.model


data class NutrientOfSearchFilterPreviewModel(
    val ID: Int,
    val name: String,
    val measure: String,
    val minValue: Float?,
    val maxValue: Float?
)