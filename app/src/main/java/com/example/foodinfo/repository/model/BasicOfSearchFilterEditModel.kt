package com.example.foodinfo.repository.model


data class BasicOfSearchFilterEditModel(
    val ID: Int,
    val infoID: Int,
    val name: String,
    val measure: String,
    val stepSize: Float,
    val rangeMin: Float,
    val rangeMax: Float,
    var minValue: Float?,
    var maxValue: Float?
)