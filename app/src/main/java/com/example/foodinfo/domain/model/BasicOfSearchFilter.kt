package com.example.foodinfo.domain.model


data class BasicOfSearchFilter(
    val ID: Int,
    val infoID: Int,
    val minValue: Float?,
    val maxValue: Float?,
    val tag: String?,
    val name: String,
    val columnName: String,
    val measure: String,
    val rangeMin: Float,
    val rangeMax: Float,
    val stepSize: Float
)