package com.example.foodinfo.repository.model.filter_field


data class NutrientOfFilterPreset(
    val infoID: Int,
    val minValue: Float? = null,
    val maxValue: Float? = null
)