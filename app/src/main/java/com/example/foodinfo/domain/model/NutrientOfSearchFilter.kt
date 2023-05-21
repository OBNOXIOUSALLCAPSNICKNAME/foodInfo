package com.example.foodinfo.domain.model


data class NutrientOfSearchFilter(
    val ID: Int,
    val infoID: Int,
    val minValue: Float?,
    val maxValue: Float?,
    val tag: String,
    val name: String,
    val measure: String,
    val hasRDI: Boolean,
    val dailyAllowance: Float,
    val precision: Int,
    val stepSize: Float,
    val rangeMin: Float,
    val rangeMax: Float
)