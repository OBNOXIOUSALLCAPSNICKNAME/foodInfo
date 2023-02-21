package com.example.foodinfo.repository.model


data class BasicOfFilterPresetModel(
    val tag: String,
    val columnName: String,
    val minValue: Float? = null,
    val maxValue: Float? = null
)