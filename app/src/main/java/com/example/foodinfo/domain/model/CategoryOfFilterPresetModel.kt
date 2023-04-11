package com.example.foodinfo.domain.model


data class CategoryOfFilterPresetModel(
    val tag: String,
    val labels: List<LabelOfFilterPresetModel>
)