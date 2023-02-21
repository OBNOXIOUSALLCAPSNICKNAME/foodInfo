package com.example.foodinfo.repository.model


data class CategoryOfFilterPresetModel(
    val tag: String,
    val labels: List<LabelOfFilterPresetModel>
)