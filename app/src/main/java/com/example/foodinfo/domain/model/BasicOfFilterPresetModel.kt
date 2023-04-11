package com.example.foodinfo.domain.model


data class BasicOfFilterPresetModel(
    val tag: String,
    val columnName: String,
    val minValue: Float? = null,
    val maxValue: Float? = null
)