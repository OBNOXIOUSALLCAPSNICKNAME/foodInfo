package com.example.foodinfo.domain.model


data class NutrientOfSearchFilterPreset(
    val infoID: Int,
    val minValue: Float?,
    val maxValue: Float?,
    val tag: String,
)