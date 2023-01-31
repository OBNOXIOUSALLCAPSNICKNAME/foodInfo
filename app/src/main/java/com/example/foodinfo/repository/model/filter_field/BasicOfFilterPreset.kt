package com.example.foodinfo.repository.model.filter_field


data class BasicOfFilterPreset(
    val columnName: String,
    val minValue: Float? = null,
    val maxValue: Float? = null
)