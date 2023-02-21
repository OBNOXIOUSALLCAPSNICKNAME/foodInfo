package com.example.foodinfo.repository.model


data class NutrientOfFilterPresetModel(
    val tag: String,
    val infoID: Int,
    val minValue: Float? = null,
    val maxValue: Float? = null
)