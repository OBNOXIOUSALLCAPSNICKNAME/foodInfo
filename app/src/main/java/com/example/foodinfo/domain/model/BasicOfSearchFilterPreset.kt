package com.example.foodinfo.domain.model


data class BasicOfSearchFilterPreset(
    val infoID: Int,
    val minValue: Float?,
    val maxValue: Float?,
    val precision: Int,
    val tag: String?,
    val columnName: String
)