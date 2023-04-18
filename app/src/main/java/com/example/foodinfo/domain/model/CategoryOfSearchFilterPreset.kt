package com.example.foodinfo.domain.model


data class CategoryOfSearchFilterPreset(
    val tag: String,
    val labels: List<LabelOfSearchFilterPreset>
)